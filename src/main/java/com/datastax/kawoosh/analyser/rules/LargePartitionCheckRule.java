package com.datastax.kawoosh.analyser.rules;


import com.datastax.kawoosh.dataStorageAdaptor.DataStorage;

public class LargePartitionCheckRule extends TableValueInRangeRule {
    public LargePartitionCheckRule(DataStorage storage) {
        super(storage, "Large Partition", "Compacted partition maximum bytes",  0d, 100000000d);
    }
}
