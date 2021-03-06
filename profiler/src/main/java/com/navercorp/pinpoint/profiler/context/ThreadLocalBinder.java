/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.profiler.context;

import com.navercorp.pinpoint.common.util.Assert;
import com.navercorp.pinpoint.profiler.util.NamedThreadLocal;

/**
 * @author emeroad
 */
public class ThreadLocalBinder<T> implements Binder<T> {

    private final ThreadLocalInitializer<T> threadLocalInitializer;

    private final ThreadLocal<T> threadLocal = new NamedThreadLocal<T>("ThreadLocalBinder") {
        @Override
        protected T initialValue() {
            return threadLocalInitializer.initialValue();
        }
    };

    public ThreadLocalBinder(ThreadLocalInitializer<T> threadLocalInitializer) {
        this.threadLocalInitializer = Assert.requireNonNull(threadLocalInitializer, "threadLocalInitializer must not be null");
    }

    @Override
    public T get() {
        return threadLocal.get();
    }

    @Override
    public void set(T t) {
        threadLocal.set(t);
    }

    @Override
    public T remove() {
        final T value = threadLocal.get();
        threadLocal.remove();
        return value;
    }

    public interface ThreadLocalInitializer<T> {
        T initialValue();
    }

}
