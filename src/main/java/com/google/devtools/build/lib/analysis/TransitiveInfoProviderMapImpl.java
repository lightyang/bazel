// Copyright 2017 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.lib.analysis;

import com.google.devtools.build.lib.collect.ImmutableSharedKeyMap;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * Implementation of {@link TransitiveInfoProvider} that uses {@link ImmutableSharedKeyMap}. For
 * memory efficiency, inheritance is used instead of aggregation as an implementation detail.
 */
class TransitiveInfoProviderMapImpl
    extends ImmutableSharedKeyMap<Class<? extends TransitiveInfoProvider>, TransitiveInfoProvider>
    implements TransitiveInfoProviderMap {

  TransitiveInfoProviderMapImpl(
      Map<Class<? extends TransitiveInfoProvider>, TransitiveInfoProvider> map) {
    super(map);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  @Override
  public <P extends TransitiveInfoProvider> P getProvider(Class<P> providerClass) {
    Class<? extends TransitiveInfoProvider> effectiveClass =
        TransitiveInfoProviderEffectiveClassHelper.get(providerClass);
    return (P) get(effectiveClass);
  }

  @Override
  public int getProviderCount() {
    return size();
  }

  @Override
  public Class<? extends TransitiveInfoProvider> getProviderClassAt(int i) {
    return keyAt(i);
  }

  @Override
  public TransitiveInfoProvider getProviderAt(int i) {
    return valueAt(i);
  }
}
