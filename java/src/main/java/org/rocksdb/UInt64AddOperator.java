// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
// This source code is licensed under both the GPLv2 (found in the
// COPYING file in the root directory) and Apache 2.0 License
// (found in the LICENSE.Apache file in the root directory).

package org.rocksdb;

/**
 * Uint64AddOperator is a merge operator that accumlates a long
 * integer value.
 */
public class UInt64AddOperator extends MergeOperator {
    /**
     * Constructs a UInt64AddOperator.
     */
    public UInt64AddOperator() {
        super(newSharedUInt64AddOperator());
    }

    private static native long newSharedUInt64AddOperator();
    @Override protected final native void disposeInternal(final long handle);
}
