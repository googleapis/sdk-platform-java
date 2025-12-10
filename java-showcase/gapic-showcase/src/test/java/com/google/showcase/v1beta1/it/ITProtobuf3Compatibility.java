package com.google.showcase.v1beta1.it;

import org.junit.jupiter.api.Test;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Constant;
import org.tensorflow.op.math.Add;
import org.tensorflow.proto.GraphDef;
import org.tensorflow.types.TInt32;

import static com.google.common.truth.Truth.assertThat;
// Tensorflow depends on protobuf 3.x gen code and runtime, we test it in showcase module to prove that it works with
// protobuf 4.33+ gen code and runtime that comes with client libraries.
public class ITProtobuf3Compatibility {

    @Test
    void testTensorflow_helloWorldExample() {
        try (Graph graph = new Graph()) {
            // Hello world example for "10 + 32" operation.
            Ops tf = Ops.create(graph);

            Constant<TInt32> expectedValue1 = tf.constant(10);
            Constant<TInt32> expectedValue2 = tf.constant(32);

            Add<TInt32> sum = tf.math.add(expectedValue1, expectedValue2);

            try (Session s = new Session(graph)) {
                try (TInt32 result = (TInt32) s.runner().fetch(sum).run().get(0)) {
                    System.out.println("10 + 32 = " + result.getInt());
                }
            }

            //GraphDef is a protobuf gen code.
            GraphDef graphDef = graph.toGraphDef();

            //Inspect the protobuf gen code
            Integer actual1 = graphDef.getNode(0).getAttrOrThrow("value").getTensor().getIntValList().get(0);
            Integer actual2 = graphDef.getNode(1).getAttrOrThrow("value").getTensor().getIntValList().get(0);

            assertThat(actual1).isEqualTo(expectedValue1);
            assertThat(actual2).isEqualTo(expectedValue2);
        }
    }
}
