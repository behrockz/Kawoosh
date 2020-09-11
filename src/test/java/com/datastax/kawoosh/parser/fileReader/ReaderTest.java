package com.datastax.kawoosh.parser.fileReader;

import com.datastax.kawoosh.common.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {
    @Test
    void readTableStats() {
        Reader reader = new TableStatReader();
        Stream<Tuple> output = reader.read("src/test/resources/cfstats");

        assertNotNull(output);

        List<String> keys = output
                .map(f -> f.getValue1())
                .collect(Collectors.toList());

        assertTrue(keys.contains("system_distributed.parent_repair_history.Average tombstones per slice (last five minutes)"));
        assertTrue(keys.contains("system.range_xfers.SSTable count"));
        assertTrue(keys.contains("detonation_persister.household_activities.Compacted partition mean bytes"));
    }

    @Test
    void readYaml() {
        Reader reader = new YamlReader();
        Stream<Tuple> output = reader.read("src/test/resources/cassandra.yaml");

        List<String> keys = output
                .map(f -> f.getValue1())
                .collect(Collectors.toList());

        assertTrue(keys.contains("dynamic_snitch_reset_interval_in_ms"));
        assertTrue(keys.contains("user_defined_function_fail_heap_mb"));
    }

    @Test
    void testTwoPlusTwoIsFour() {
        assertEquals(4, 2 + 2);
    }
}