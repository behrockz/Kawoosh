package com.datastax.kawoosh.parser.fileReader;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    @Test
    public void readCfStats() {
        Reader reader = new TableStatReader();
        Stream<Pair> output = reader.read("src/test/resources/cfstats");
        assertNotNull(output);
        List<String> keys = output
                .map(f -> f.getKey())
                .collect(Collectors.toList());

        assertTrue(keys.contains("system_distributed.parent_repair_history.Average tombstones per slice (last five minutes)"));
        assertTrue(keys.contains("system.range_xfers.SSTable count"));
        assertTrue(keys.contains("activity_persister.household_activities.Compacted partition mean bytes"));
    }

    @Test
    void testTwoPlusTwoIsFour() {
        assertEquals(4, 2 + 2);
    }
}