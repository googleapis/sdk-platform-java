#!/bin/bash

test_directory=$(pwd)

find "$test_directory" -maxdepth 1 -type f -name "IT*.java" | while read file; do

    # Replace imports
    sed -i 's/import org.junit.Test;/import org.junit.jupiter.api.Test;/g' "$file"
    sed -i 's/import org.junit.Before;/import org.junit.jupiter.api.BeforeEach;/g' "$file"
    sed -i 's/import org.junit.After;/import org.junit.jupiter.api.AfterEach;/g' "$file"
    sed -i 's/import org.junit.BeforeClass;/import org.junit.jupiter.api.BeforeAll;/g' "$file"
    sed -i 's/import org.junit.AfterClass;/import org.junit.jupiter.api.AfterAll;/g' "$file"
    sed -i 's/import org.junit.Ignore;/import org.junit.jupiter.api.Disabled;/g' "$file"
    sed -i 's/import org.junit.Assert;/import org.junit.jupiter.api.Assertions;/g' "$file"
    sed -i 's/import static org.junit.Assert.assertEquals;/import static org.junit.jupiter.api.Assertions.assertEquals;/g' "$file"
    sed -i 's/import static org.junit.Assert.assertTrue;/import static org.junit.jupiter.api.Assertions.assertTrue;/g' "$file"
    sed -i 's/import static org.junit.Assert.assertNotSame;/import static org.junit.jupiter.api.Assertions.assertNotSame;/g' "$file"
    sed -i 's/import static org.junit.Assert.assertThrows;/import static org.junit.jupiter.api.Assertions.assertThrows;/g' "$file"

    # Replace annotations (with potential whitespace variations and end-of-word check)
    sed -i 's/@Test(timeout = 15000L)/@Test\n  @Timeout(15)/g' "$file"
    sed -i 's/@[[:space:]]*Before\b/@BeforeEach /g' "$file" # Added word boundary
    sed -i 's/@[[:space:]]*After\b/@AfterEach /g' "$file"   # Added word boundary
    sed -i 's/@[[:space:]]*BeforeClass\b/@BeforeAll /g' "$file"  # Added word boundary
    sed -i 's/@[[:space:]]*AfterClass\b/@AfterAll /g' "$file"   # Added word boundary
    sed -i 's/@[[:space:]]*Ignore\b/@Disabled /g' "$file"   # Added word boundary

    # Replace assertion methods (examples)
    sed -i 's/Assert.assertEquals/Assertions.assertEquals/g' "$file"
    sed -i 's/Assert.assertTrue/Assertions.assertTrue/g' "$file"
    sed -i 's/Assert.assertFalse/Assertions.assertFalse/g' "$file"
    sed -i 's/Assert.assertNull/Assertions.assertNull/g' "$file"
    sed -i 's/Assert.assertNotNull/Assertions.assertNotNull/g' "$file"
    sed -i 's/Assert.fail/Assertions.fail/g' "$file"
    # Add more assertion replacements as needed

    # Replace ExpectedException rule (example)
    sed -i 's/@Rule\n.*ExpectedException thrown.*/assertThrows(Exception.class, () -> {/g' "$file"
    # Close the lambda function with a }); after the code that throws the exception

    sed -i 's/@Rule public TemporaryFolder/MANUAL CHANGES HERE!!/g' "$file"

    # Remove public modifiers
    sed -i 's/public class /class /g' "$file"
#    sed -i 's/public void /void /g' "$file"
    # Remove public modifiers from test methods ONLY
#    sed -i '/^[[:space:]]*@(Test|BeforeEach|AfterEach|BeforeAll|AfterAll|Disabled)/ s/public void /void /g' "$file"
#    sed -i -E 's/([[:space:]]+@(Test|BeforeEach|AfterEach|BeforeAll|AfterAll|Disabled)[^\n]*)\n[[:space:]]+public[[:space:]]+void/\1\n    void/g' "$file"
#perl -0777 -i -pe 's/(@(Test|BeforeEach|AfterEach|BeforeAll|AfterAll|Disabled)(?:\s*\(.*?\))?\s*\n\s*)(static|final)?\s*public\s+(void\s+\w+\s*\((?:.*\n)*?\s*\)\s*(?:throws\s+\w+\s*)?\{)/$1$3$4/sg' "$file"
#perl -0777 -i -pe 's/(@(Test|BeforeEach|AfterEach|BeforeAll|AfterAll|Disabled)(?:\s*\(.*?\))?\s*\n\s*)(static|final)?\s*public\s+void\s+(\w+)\s*\((?:.*\n)*?\s*\)(?:\s*throws\s+(.*?))?\s*\{/$1\n    $3void $4($5) throws $6{/sg' "$file"

done

echo "JUnit 4 to JUnit 5 conversion completed for files in $test_directory"