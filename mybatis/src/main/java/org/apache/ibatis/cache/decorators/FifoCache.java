/**
 *    Copyright ${license.git.copyrightYears} the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.cache.decorators;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;

/**
 * FIFO (first in, first out) cache decorator
 *
 * @author Clinton Begin
 *
 * FifoCache ，实现 Cache 接口，基于先进先出的淘汰机制的 Cache 实现类。
 */
public class FifoCache implements Cache {

  private final Cache delegate;
  //双端队列，记录缓存键的添加
  private final Deque<Object> keyList;
  //队列上限
  private int size;

  public FifoCache(Cache delegate) {
    this.delegate = delegate;
    this.keyList = new LinkedList<Object>();// 使用了 LinkedList
    this.size = 1024;// 默认为 1024
  }

  @Override
  public String getId() {
    return delegate.getId();
  }

  @Override
  public int getSize() {
    return delegate.getSize();
  }

  public void setSize(int size) {
    this.size = size;
  }

  @Override
  public void putObject(Object key, Object value) {
    cycleKeyList(key);
    delegate.putObject(key, value);
  }

  @Override
  public Object getObject(Object key) {
    return delegate.getObject(key);
  }

  @Override
  public Object removeObject(Object key) {
    return delegate.removeObject(key);
  }

  @Override
  public void clear() {
    delegate.clear();
    keyList.clear();
  }

  @Override
  public ReadWriteLock getReadWriteLock() {
    return null;
  }

  /**
   * 目前 FifoCache 的逻辑实现上，有一定的问题，主要有两点。
   * <1> 处，如果重复添加一个缓存，那么在 keyList 里会存储两个，占用了缓存上限的两个名额。
   * <2> 处，在移除指定缓存时，不会移除 keyList 里占用的一个名额。
   */
  private void cycleKeyList(Object key) {
    // <1> 添加到 keyList 对位
    keyList.addLast(key);
    if (keyList.size() > size) {
      // 超过上限，将队首位移除
      Object oldestKey = keyList.removeFirst();
      delegate.removeObject(oldestKey);
    }
  }

}
