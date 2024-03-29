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

package top.zhacker.ddd.collaboration.domain.collaborator;
/**
 *  作者-值对像
 */
public final class Author extends Collaborator {

    private static final long serialVersionUID = 1L;

    public Author(String anIdentity, String aName, String anEmailAddress) {
        super(anIdentity, aName, anEmailAddress);
    }

    protected Author() {
        super();
    }

    @Override
    protected int hashPrimeValue() {
        return 19;
    }
}
