//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package top.zhacker.ddd.agilepm.domain.product;

import top.zhacker.ddd.agilepm.domain.tenant.TenantId;

import java.util.Collection;

/**
 * 产品资源库
 */
public interface ProductRepository {

    public Collection<Product> allProductsOfTenant(TenantId aTenantId);

    public ProductId nextIdentity();

    public Product productOfDiscussionInitiationId(TenantId aTenantId, String aDiscussionInitiationId);

    public Product productOfId(TenantId aTenantId, ProductId aProductId);

    public void remove(Product aProduct);

    public void removeAll(Collection<Product> aProductCollection);

    public void save(Product aProduct);

    public void saveAll(Collection<Product> aProductCollection);
}
