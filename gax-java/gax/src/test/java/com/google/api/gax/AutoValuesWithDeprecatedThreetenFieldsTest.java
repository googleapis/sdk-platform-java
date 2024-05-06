package com.google.api.gax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.api.gax.util.ThreetenFieldUpgrade;
import com.google.api.gax.util.ThreetenFieldUpgrade.FieldRole;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.Generated;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.reflections.Reflections;

@RunWith(JUnit4.class)
public class AutoValuesWithDeprecatedThreetenFieldsTest {

  private static final Logger logger =
      Logger.getLogger(AutoValuesWithDeprecatedThreetenFieldsTest.class.getSimpleName());

  @Test
  public void testAutoValueClassesWithThreetenMethods()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    List<Class<?>> targetClasses = getAutoValueClassesWithThreetenMethods();
    for (Class c : targetClasses) {
      testAutoValueClassWithThreetenMethods(c);
    }
  }

  private void testAutoValueClassWithThreetenMethods(Class autoValueClass)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    logger.info(String.format("Testing threeten methods for class %s", autoValueClass.getName()));

    // We first group the methods by key. Each key should contain four methods corresponding to a
    // type declared in the ThreetenFieldUpgrade.FieldRole enum.
    Map<String, List<Method>> methodGroups = new HashMap<>();

    // The FieldRole.JAVA_TIME_GETTER and FieldRole.THREETEN_GETTER methods are found in the main
    // abstract class. This loop searchs for them.
    for (Method m : autoValueClass.getDeclaredMethods()) {
      addToMethodGroupIfHasThreetenFieldUpgrateAnnotation(methodGroups, m);
    }

    // The FieldRole.JAVA_TIME_SETTER and FieldRole.THREETEN_SETTER methods are found in the builder
    // nested class.
    // We first obtain the Builder class
    Class<?> builderClass =
        Arrays.stream(autoValueClass.getDeclaredClasses())
            .filter(b -> b.getSimpleName().contains("Builder"))
            .findFirst()
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        String.format(
                            "Failed to find Builder class for AutoValue class %s",
                            autoValueClass.getName())));

    // Then we loop over the methods to group the methods annotated with ThreetenFieldUpgrade
    for (Method m : builderClass.getDeclaredMethods()) {
      addToMethodGroupIfHasThreetenFieldUpgrateAnnotation(methodGroups, m);
    }

    assertFalse("No method groups could be constructed", methodGroups.isEmpty());

    // Now, we confirm each method group is complete, sane, and we confirm its behavior by testing
    // the getters and setters
    for (Entry<String, List<Method>> methodGroup : methodGroups.entrySet()) {
      String groupName = methodGroup.getKey();
      logger.info(String.format("Organizing methods for group '%s'", groupName));
      Map<ThreetenFieldUpgrade.FieldRole, Method> organizedMethods =
          organizeMethods(groupName, methodGroup.getValue());
      logger.info(String.format("Confirming structure of method group '%s", groupName));
      confirmGetterAndSetterStructure(organizedMethods);
      logger.info(String.format("Confirming behavior of method group '%s", groupName));
      confirmGetterAndSetterBehavior(organizedMethods, autoValueClass, builderClass);
    }
  }

  private void confirmGetterAndSetterBehavior(
      Map<FieldRole, Method> organizedMethods, Class autoValueClass, Class<?> builderClass)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method javaTimeGetter = organizedMethods.get(FieldRole.JAVA_TIME_GETTER);
    Method threetenGetter = organizedMethods.get(FieldRole.THREETEN_GETTER);
    Method javaTimeSetter = organizedMethods.get(FieldRole.JAVA_TIME_SETTER);
    Method threetenSetter = organizedMethods.get(FieldRole.THREETEN_SETTER);
    final long testValue = 123l;
    // We use the java.time getter's return type to identify which time class (Duration or Instant)
    // we are dealing with. At this point we already ensured consistency of return and parameter
    // types
    boolean isDuration = javaTimeGetter.getReturnType().equals(java.time.Duration.class);
    final Object testJavaTimeObjectValue =
        isDuration
            ? java.time.Duration.ofMillis(testValue)
            : java.time.Instant.ofEpochMilli(testValue);
    final Object testThreetenObjectValue =
        isDuration
            ? org.threeten.bp.Duration.ofMillis(testValue)
            : org.threeten.bp.Instant.ofEpochMilli(testValue);

    confirmGetterAndSetterBehaviorUsingSingleSetter(
        javaTimeGetter,
        threetenGetter,
        javaTimeSetter,
        autoValueClass,
        builderClass,
        testJavaTimeObjectValue,
        testJavaTimeObjectValue,
        testThreetenObjectValue);
    confirmGetterAndSetterBehaviorUsingSingleSetter(
        javaTimeGetter,
        threetenGetter,
        threetenSetter,
        autoValueClass,
        builderClass,
        testThreetenObjectValue,
        testJavaTimeObjectValue,
        testThreetenObjectValue);
  }

  private void confirmGetterAndSetterBehaviorUsingSingleSetter(
      Method javaTimeGetter,
      Method threetenGetter,
      Method setter,
      Class autoValueClass,
      Class builderClass,
      Object testSetterValue,
      Object testJavaTimeObjectValue,
      Object testThreetenObjectValue)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Object builder = autoValueClass.getDeclaredMethod("newBuilder").invoke(autoValueClass);
    setter.invoke(builder, testSetterValue);
    Object builtObject = builderClass.getMethod("build").invoke(builder);
    assertEquals(testJavaTimeObjectValue, javaTimeGetter.invoke(builtObject));
    assertEquals(testThreetenObjectValue, threetenGetter.invoke(builtObject));
  }

  private void confirmGetterAndSetterStructure(Map<FieldRole, Method> organizedMethods) {
    Method javaTimeGetter = organizedMethods.get(FieldRole.JAVA_TIME_GETTER);
    Method threetenGetter = organizedMethods.get(FieldRole.THREETEN_GETTER);
    Method javaTimeSetter = organizedMethods.get(FieldRole.JAVA_TIME_SETTER);
    Method threetenSetter = organizedMethods.get(FieldRole.THREETEN_SETTER);
    // We will inspect the java.time getter to see if we are dealing with Duration or Instant and
    // will use this info to confirm consistency among the methods
    Class javaTimeGetterReturnType = javaTimeGetter.getReturnType();
    assertTrue(
        "The java.time getter should return either java.time.Duration or java.time.Instant",
        ImmutableList.of("java.time.Duration", "java.time.Instant")
            .contains(javaTimeGetterReturnType.getName()));

    // We will use the following variable to ensure that the rest of the methods are either Duration
    // or Instant, depending on what we found about the java.time getter
    String commonReturnTypeName = javaTimeGetterReturnType.getSimpleName();

    assertEquals(
        String.format("The threeten getter should return org.threeten.%s", commonReturnTypeName),
        String.format("org.threeten.bp.%s", commonReturnTypeName),
        threetenGetter.getReturnType().getName());
    assertEquals(
        "AutoValue getters should have no parameters", 0, javaTimeGetter.getParameterCount());
    assertEquals(
        "AutoValue getters should have no parameters", 0, threetenGetter.getParameterCount());
    assertEquals(
        String.format(
            "The java.time getter should be named the same as the threeten getter plus the %s suffix",
            commonReturnTypeName),
        threetenGetter.getName().concat(commonReturnTypeName),
        javaTimeGetter.getName());

    // now we confirm the setter structure
    assertEquals(
        "Setters should have the same name", javaTimeSetter.getName(), threetenSetter.getName());
    assertEquals("Setters should have a single parameter", 1, javaTimeSetter.getParameterCount());
    assertEquals("Setters should have a single parameter", 1, threetenSetter.getParameterCount());
    assertEquals(
        String.format(
            "The java.time setter should take a java.time.%s parameter", commonReturnTypeName),
        "java.time.".concat(commonReturnTypeName),
        javaTimeSetter.getParameterTypes()[0].getName());
    assertEquals(
        String.format(
            "The threeten setter should take a org.threeten.bp.%s parameter", commonReturnTypeName),
        "org.threeten.bp.".concat(commonReturnTypeName),
        threetenSetter.getParameterTypes()[0].getName());
  }

  private void addToMethodGroupIfHasThreetenFieldUpgrateAnnotation(
      Map<String, List<Method>> storage, Method m) {
    if (!m.isAnnotationPresent(ThreetenFieldUpgrade.class)) {
      return;
    }
    ThreetenFieldUpgrade annotation = m.getAnnotation(ThreetenFieldUpgrade.class);
    if (!storage.containsKey(annotation.key())) {
      storage.put(annotation.key(), new ArrayList<>());
    }
    storage.get(annotation.key()).add(m);
  }

  private Map<ThreetenFieldUpgrade.FieldRole, Method> organizeMethods(
      String groupName, List<Method> methods) {
    Map<ThreetenFieldUpgrade.FieldRole, Method> result = new HashMap<>();
    for (Method m : methods) {
      ThreetenFieldUpgrade annotation = m.getAnnotation(ThreetenFieldUpgrade.class);
      assertFalse(
          String.format(
              "Method group %s has two or more methods with the %s FieldRole",
              groupName, annotation.role()),
          result.containsKey(annotation.role()));
      result.put(annotation.role(), m);
    }
    assertEquals(
        String.format("Method group %s does not contain all FieldRoles", groupName),
        4,
        result.size());
    return result;
  }

  private List<Class<?>> getAutoValueClassesWithThreetenMethods() {
    Reflections reflections = new Reflections("com.google.api.gax");
    Set<Class<?>> allAutoValues = reflections.getTypesAnnotatedWith(AutoValue.class);
    List<Class<?>> result = new ArrayList<>();
    for (Class c : allAutoValues) {
      // we don't want generated AutoValue_.* classes
      boolean isAutoGenerated =
          c.isAnnotationPresent(Generated.class) || c.getSimpleName().startsWith("AutoValue_");
      if (isAutoGenerated) {
        continue;
      }
      List<Method> methods = Arrays.asList(c.getDeclaredMethods());
      boolean hasThreetenAnnotatedMethods =
          methods.stream().anyMatch(m -> m.isAnnotationPresent(ThreetenFieldUpgrade.class));
      if (hasThreetenAnnotatedMethods) {
        result.add(c);
      }
    }
    return result;
  }
}
