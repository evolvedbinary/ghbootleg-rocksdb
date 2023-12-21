// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
//  This source code is licensed under both the GPLv2 (found in the
//  COPYING file in the root directory) and Apache 2.0 License
//  (found in the LICENSE.Apache file in the root directory).

package org.rocksdb;

import java.nio.ByteBuffer;

/**
 * WriteBatch holds a collection of updates to apply atomically to a DB.
 * <p>
 * The updates are applied in the order in which they are added
 * to the WriteBatch.  For example, the value of "key" will be "v3"
 * after the following batch is written:
 * <p>
 *    batch.put("key", "v1");
 *    batch.remove("key");
 *    batch.put("key", "v2");
 *    batch.put("key", "v3");
 * <p>
 * Multiple threads can invoke const methods on a WriteBatch without
 * external synchronization, but if any of the threads may call a
 * non-const method, all threads accessing the same WriteBatch must use
 * external synchronization.
 */
public class WriteBatch extends AbstractWriteBatch {
  /**
   * Constructs a WriteBatch instance.
   */
  public WriteBatch() {
    this(0);
  }

  /**
   * Constructs a WriteBatch instance with a given size.
   *
   * @param reserved_bytes reserved size for WriteBatch
   */
  public WriteBatch(final int reserved_bytes) {
    super(newWriteBatch(reserved_bytes));
  }

  /**
   * Constructs a WriteBatch instance from a serialized representation
   * as returned by {@link #data()}.
   *
   * @param serialized the serialized representation.
   */
  public WriteBatch(final byte[] serialized) {
    super(newWriteBatch(serialized, serialized.length));
  }

  /**
   * Support for iterating over the contents of a batch.
   *
   * @param handler A handler that is called back for each
   *                update present in the batch
   *
   * @throws RocksDBException If we cannot iterate over the batch
   */
  public void iterate(final Handler handler) throws RocksDBException {
    iterate(nativeHandle_, handler.nativeHandle_);
  }

  /**
   * Retrieve the serialized version of this batch.
   *
   * @return the serialized representation of this write batch.
   *
   * @throws RocksDBException if an error occurs whilst retrieving
   *   the serialized batch data.
   */
  public byte[] data() throws RocksDBException {
    return data(nativeHandle_);
  }

  /**
   * Retrieve data size of the batch.
   *
   * @return the serialized data size of the batch.
   */
  public long getDataSize() {
    return getDataSize(nativeHandle_);
  }

  /**
   * Returns true if Put will be called during Iterate.
   *
   * @return true if Put will be called during Iterate.
   */
  public boolean hasPut() {
    return hasPut(nativeHandle_);
  }

  /**
   * Returns true if Delete will be called during Iterate.
   *
   * @return true if Delete will be called during Iterate.
   */
  public boolean hasDelete() {
    return hasDelete(nativeHandle_);
  }

  /**
   * Returns true if SingleDelete will be called during Iterate.
   *
   * @return true if SingleDelete will be called during Iterate.
   */
  public boolean hasSingleDelete() {
    return hasSingleDelete(nativeHandle_);
  }

  /**
   * Returns true if DeleteRange will be called during Iterate.
   *
   * @return true if DeleteRange will be called during Iterate.
   */
  public boolean hasDeleteRange() {
    return hasDeleteRange(nativeHandle_);
  }

  /**
   * Returns true if Merge will be called during Iterate.
   *
   * @return true if Merge will be called during Iterate.
   */
  public boolean hasMerge() {
    return hasMerge(nativeHandle_);
  }

  /**
   * Returns true if MarkBeginPrepare will be called during Iterate.
   *
   * @return true if MarkBeginPrepare will be called during Iterate.
   */
  public boolean hasBeginPrepare() {
    return hasBeginPrepare(nativeHandle_);
  }

  /**
   * Returns true if MarkEndPrepare will be called during Iterate.
   *
   * @return true if MarkEndPrepare will be called during Iterate.
   */
  public boolean hasEndPrepare() {
    return hasEndPrepare(nativeHandle_);
  }

  /**
   * Returns true if MarkCommit will be called during Iterate.
   *
   * @return true if MarkCommit will be called during Iterate.
   */
  public boolean hasCommit() {
    return hasCommit(nativeHandle_);
  }

  /**
   * Returns true if MarkRollback will be called during Iterate.
   *
   * @return true if MarkRollback will be called during Iterate.
   */
  public boolean hasRollback() {
    return hasRollback(nativeHandle_);
  }

  @Override
  public WriteBatch getWriteBatch() {
    return this;
  }

  /**
   * Marks this point in the WriteBatch as the last record to
   * be inserted into the WAL, provided the WAL is enabled.
   */
  public void markWalTerminationPoint() {
    markWalTerminationPoint(nativeHandle_);
  }

  /**
   * Gets the WAL termination point.
   * <p>
   * See {@link #markWalTerminationPoint()}
   *
   * @return the WAL termination point
   */
  public SavePoint getWalTerminationPoint() {
    return getWalTerminationPoint(nativeHandle_);
  }

  @Override
  WriteBatch getWriteBatch(final long handle) {
    return this;
  }

  /**
   * <p>Private WriteBatch constructor which is used to construct
   * WriteBatch instances from C++ side. As the reference to this
   * object is also managed from C++ side the handle will be disowned.</p>
   *
   * @param nativeHandle address of native instance.
   */
  WriteBatch(final long nativeHandle) {
    this(nativeHandle, false);
  }

  /**
   * <p>Private WriteBatch constructor which is used to construct
   * WriteBatch instances. </p>
   *
   * @param nativeHandle address of native instance.
   * @param owningNativeHandle whether to own this reference from the C++ side or not
   */
  WriteBatch(final long nativeHandle, final boolean owningNativeHandle) {
    super(nativeHandle);
    if(!owningNativeHandle)
      disOwnNativeHandle();
  }

  @Override protected final native void disposeInternal(final long handle);
  @Override final native int count0(final long handle);
  @Override final native void put(final long handle, final byte[] key,
      final int keyLen, final byte[] value, final int valueLen);
  @Override final native void put(final long handle, final byte[] key,
      final int keyLen, final byte[] value, final int valueLen,
      final long cfHandle);
  @Override
  final native void putDirect(final long handle, final ByteBuffer key, final int keyOffset,
      final int keyLength, final ByteBuffer value, final int valueOffset, final int valueLength,
      final long cfHandle);
  @Override final native void merge(final long handle, final byte[] key,
      final int keyLen, final byte[] value, final int valueLen);
  @Override final native void merge(final long handle, final byte[] key,
      final int keyLen, final byte[] value, final int valueLen,
      final long cfHandle);
  @Override final native void delete(final long handle, final byte[] key,
      final int keyLen) throws RocksDBException;
  @Override final native void delete(final long handle, final byte[] key,
      final int keyLen, final long cfHandle) throws RocksDBException;
  @Override final native void singleDelete(final long handle, final byte[] key,
      final int keyLen) throws RocksDBException;
  @Override final native void singleDelete(final long handle, final byte[] key,
      final int keyLen, final long cfHandle) throws RocksDBException;
  @Override
  final native void deleteDirect(final long handle, final ByteBuffer key, final int keyOffset,
      final int keyLength, final long cfHandle) throws RocksDBException;
  @Override
  final native void deleteRange(final long handle, final byte[] beginKey, final int beginKeyLen,
      final byte[] endKey, final int endKeyLen);
  @Override
  final native void deleteRange(final long handle, final byte[] beginKey, final int beginKeyLen,
      final byte[] endKey, final int endKeyLen, final long cfHandle);
  @Override final native void putLogData(final long handle,
      final byte[] blob, final int blobLen) throws RocksDBException;
  @Override final native void clear0(final long handle);
  @Override final native void setSavePoint0(final long handle);
  @Override final native void rollbackToSavePoint0(final long handle);
  @Override final native void popSavePoint(final long handle) throws RocksDBException;
  @Override final native void setMaxBytes(final long nativeHandle,
    final long maxBytes);

  private static native long newWriteBatch(final int reserved_bytes);
  private static native long newWriteBatch(final byte[] serialized, final int serializedLength);
  private native void iterate(final long handle, final long handlerHandle)
      throws RocksDBException;
  private native byte[] data(final long nativeHandle) throws RocksDBException;
  private native long getDataSize(final long nativeHandle);
  private native boolean hasPut(final long nativeHandle);
  private native boolean hasDelete(final long nativeHandle);
  private native boolean hasSingleDelete(final long nativeHandle);
  private native boolean hasDeleteRange(final long nativeHandle);
  private native boolean hasMerge(final long nativeHandle);
  private native boolean hasBeginPrepare(final long nativeHandle);
  private native boolean hasEndPrepare(final long nativeHandle);
  private native boolean hasCommit(final long nativeHandle);
  private native boolean hasRollback(final long nativeHandle);
  private native void markWalTerminationPoint(final long nativeHandle);
  private native SavePoint getWalTerminationPoint(final long nativeHandle);

  /**
   * Handler callback for iterating over the contents of a batch.
   */
  public abstract static class Handler extends RocksCallbackObject {
    /**
     * Constructs a Handler.
     */
    public Handler() {
      super(0L);
    }

    @Override
    protected long initializeNative(final long... nativeParameterHandles) {
      return createNewHandler0();
    }

    /**
     * Put operation callback.
     *
     * @param columnFamilyId the id of the column family that the operation was performed on.
     * @param key the key from the put operation.
     * @param value the value from the put operation.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void put(final int columnFamilyId, final byte[] key,
        final byte[] value) throws RocksDBException;

    /**
     * Put operation callback.
     *
     * @param key the key from the put operation.
     * @param value the value from the put operation.
     */
    public abstract void put(final byte[] key, final byte[] value);

    /**
     * Merge operation callback.
     *
     * @param columnFamilyId the id of the column family that the operation was performed on.
     * @param key the key from the merge operation.
     * @param value the value from the merge operation.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void merge(final int columnFamilyId, final byte[] key,
        final byte[] value) throws RocksDBException;

    /**
     * Merge operation callback.
     *
     * @param key the key from the merge operation.
     * @param value the value from the merge operation.
     */
    public abstract void merge(final byte[] key, final byte[] value);

    /**
     * Delete operation callback.
     *
     * @param columnFamilyId the id of the column family that the operation was performed on.
     * @param key the key from the delete operation.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void delete(final int columnFamilyId, final byte[] key)
        throws RocksDBException;

    /**
     * Delete operation callback.
     *
     * @param key the key from the delete operation.
     */
    public abstract void delete(final byte[] key);

    /**
     * Single Delete operation callback.
     *
     * @param columnFamilyId the id of the column family that the operation was performed on.
     * @param key the key from the single delete operation.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void singleDelete(final int columnFamilyId,
        final byte[] key) throws RocksDBException;

    /**
     * Single Delete operation callback.
     *
     * @param key the key from the single delete operation.
     */
    public abstract void singleDelete(final byte[] key);

    /**
     * Delete Range operation callback.
     *
     * @param columnFamilyId the id of the column family that the operation was performed on.
     * @param beginKey the begin key from the delete range operation.
     * @param endKey the end key from the delete range operation.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void deleteRange(final int columnFamilyId,
        final byte[] beginKey, final byte[] endKey) throws RocksDBException;

    /**
     * Delete Range operation callback.
     *
     * @param beginKey the begin key from the delete range operation.
     * @param endKey the end key from the delete range operation.
     */
    public abstract void deleteRange(final byte[] beginKey,
        final byte[] endKey);

    /**
     * Log Data operation callback.
     *
     * @param blob the blob from the log data operation.
     */
    public abstract void logData(final byte[] blob);

    /**
     * Put Blob Index operation callback.
     *
     * @param columnFamilyId the id of the column family that the operation was performed on.
     * @param key the key from the put blob index operation.
     * @param value the value from the put blob index operation.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void putBlobIndex(final int columnFamilyId,
        final byte[] key, final byte[] value) throws RocksDBException;

    /**
     * Mark Begin Prepare operation callback.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void markBeginPrepare() throws RocksDBException;

    /**
     * Mark End Prepare operation callback.
     *
     * @param xid the transaction id.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void markEndPrepare(final byte[] xid)
        throws RocksDBException;

    /**
     * Mark Noop operation callback.
     *
     * @param emptyBatch true if the batch was empty, false otherwise.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void markNoop(final boolean emptyBatch)
        throws RocksDBException;

    /**
     * Mark Rollback operation callback.
     *
     * @param xid the transaction id.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void markRollback(final byte[] xid)
        throws RocksDBException;

    /**
     * Mark Commit operation callback.
     *
     * @param xid the transaction id.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void markCommit(final byte[] xid)
        throws RocksDBException;

    /**
     * Mark Commit With Timestamp operation callback.
     *
     * @param xid the transaction id.
     * @param ts the timestamp.
     *
     * @throws RocksDBException to signal an error from the handler.
     */
    public abstract void markCommitWithTimestamp(final byte[] xid, final byte[] ts)
        throws RocksDBException;

    /**
     * Called by the underlying iterator
     * {@link WriteBatch#iterate(Handler)}. If it returns false,
     * iteration is halted. Otherwise, it continues
     * iterating. The default implementation always
     * returns true.
     *
     * @return boolean value indicating if the
     *     iteration is halted.
     */
    public boolean shouldContinue() {
      return true;
    }

    private native long createNewHandler0();
  }

  /**
   * A structure for describing the save point in the Write Batch.
   */
  public static class SavePoint {
    private long size;
    private long count;
    private long contentFlags;

    /**
     * Constructs a SavePoint.
     *
     * @param size the size
     * @param count the count
     * @param contentFlags the content flags
     */
    public SavePoint(final long size, final long count,
        final long contentFlags) {
      this.size = size;
      this.count = count;
      this.contentFlags = contentFlags;
    }

    /**
     * Clear the save point data.
     */
    public void clear() {
      this.size = 0;
      this.count = 0;
      this.contentFlags = 0;
    }

    /**
     * Get the size of the serialized representation.
     *
     * @return the size of the serialized representation.
     */
    public long getSize() {
      return size;
    }

    /**
     * Get the number of elements.
     *
     * @return the number of elements.
     */
    public long getCount() {
      return count;
    }

    /**
     * Get the content flags.
     *
     * @return the content flags.
     */
    public long getContentFlags() {
      return contentFlags;
    }

    /**
     * Determines if {@link #clear()} was
     * called.
     *
     * @return true if {@link #clear()} was called and the save point remains empty, false otherwise.
     */
    public boolean isCleared() {
      return (size | count | contentFlags) == 0;
    }
  }
}
