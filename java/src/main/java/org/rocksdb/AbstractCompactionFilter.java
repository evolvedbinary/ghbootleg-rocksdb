// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
//  This source code is licensed under both the GPLv2 (found in the
//  COPYING file in the root directory) and Apache 2.0 License
//  (found in the LICENSE.Apache file in the root directory).
package org.rocksdb;

/**
 * A CompactionFilter allows an application to modify/delete a key-value at
 * the time of compaction.
 * <p>
 * At present, we just permit an overriding Java class to wrap a C++
 * implementation
 *
 * @param <T> the concrete type of the {@link AbstractSlice} that the Compaction Filter uses.
 */
public abstract class AbstractCompactionFilter<T extends AbstractSlice<?>>
    extends RocksObject {
  /**
   * Context of the Compaction Filter.
   */
  public static class Context {
    private final boolean fullCompaction;
    private final boolean manualCompaction;

    /**
     * Context constructor.
     *
     * @param fullCompaction true to filter full compaction, false otherwise.
     * @param manualCompaction true to filter manual compaction, false otherwise.
     */
    public Context(final boolean fullCompaction, final boolean manualCompaction) {
      this.fullCompaction = fullCompaction;
      this.manualCompaction = manualCompaction;
    }

    /**
     * Does this compaction run include all data files
     *
     * @return true if this is a full compaction run
     */
    public boolean isFullCompaction() {
      return fullCompaction;
    }

    /**
     * Is this compaction requested by the client,
     * or is it occurring as an automatic compaction process
     *
     * @return true if the compaction was initiated by the client
     */
    public boolean isManualCompaction() {
      return manualCompaction;
    }
  }

  /**
   * Constructor to be called by subclasses to set the
   * handle to the underlying C++ object.
   *
   * @param nativeHandle reference to the value of the C++ pointer pointing to the underlying native
   *     RocksDB C++ Compaction Filter.
   */
  protected AbstractCompactionFilter(final long nativeHandle) {
    super(nativeHandle);
  }

  /**
   * Deletes underlying C++ compaction pointer.
   * <p>
   * Note that this function should be called only after all
   * RocksDB instances referencing the compaction filter are closed.
   * Otherwise, an undefined behavior will occur.
   */
  @Override
  protected final native void disposeInternal(final long handle);
}
