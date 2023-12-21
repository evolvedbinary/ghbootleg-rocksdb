// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
//  This source code is licensed under both the GPLv2 (found in the
//  COPYING file in the root directory) and Apache 2.0 License
//  (found in the LICENSE.Apache file in the root directory).

package org.rocksdb;

/**
 * RocksObject is an implementation of {@link AbstractNativeReference} which
 * has an immutable and therefore thread-safe reference to the underlying
 * native C++ RocksDB object.
 * <p>
 * RocksObject is the base-class of almost all RocksDB classes that have a
 * pointer to some underlying native C++ {@code rocksdb} object.</p>
 * <p>
 * The use of {@code RocksObject} should always be preferred over
 * {@link RocksMutableObject}.</p>
 */
public abstract class RocksObject extends AbstractImmutableNativeReference {

  /**
   * An immutable reference to the value of the C++ pointer pointing to some
   * underlying native RocksDB C++ object.
   */
  protected final long nativeHandle_;

  /**
   * Constructor to be called by subclasses to set the
   * handle to the underlying C++ object.
   *
   * @param nativeHandle reference to the value of the C++ pointer pointing to the underlying native
   *     RocksDB C++ object.
   */
  protected RocksObject(final long nativeHandle) {
    super(true);
    this.nativeHandle_ = nativeHandle;
  }

  /**
   * Deletes underlying C++ object pointer.
   */
  @Override
  protected void disposeInternal() {
    disposeInternal(nativeHandle_);
  }

  /**
   * Deletes any underlying native C++ objects which are owned by this object.
   * All subclasses of {@code RocksObject} must
   * implement this to release their underlying native C++ objects.
   *
   * @param handle reference to the value of the C++ pointer pointing to some underlying native
   *     RocksDB C++ object.
   */
  protected abstract void disposeInternal(final long handle);
}
