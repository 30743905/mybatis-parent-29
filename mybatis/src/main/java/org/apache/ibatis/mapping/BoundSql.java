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
package org.apache.ibatis.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;

/**
 * An actual SQL String got from an {@link SqlSource} after having processed any dynamic content.
 * The SQL may have SQL placeholders "?" and an list (ordered) of an parameter mappings 
 * with the additional information for each parameter (at least the property name of the input object to read 
 * the value from). 
 * </br>
 * Can also have additional parameters that are created by the dynamic language (for loops, bind...).
 *
 * @author Clinton Begin
 *
 *我们在执行SQL时，一个重要的任务是将SQL语句解析出来。我们都知道SQL是配置在映射文件中的，但由于映射文件中的SQL可能会包含占位符#{}，
 * 以及动态 SQL 标签，比如 <if>、<where> 等。因此，我们并不能直接使用映射文件中配置的SQL。MyBatis会将映射文件中的SQL解析成一组SQL片段。
 * 如果某个片段中也包含动态SQL相关的标签，那么，MyBatis会对该片段再次进行分片。最终，一个SQL配置将会被解析成一个SQL片段树。
 *
 * 我们需要对片段树进行解析，以便从每个片段对象中获取相应的内容。然后将这些内容组合起来即可得到一个完成的SQL语句，这个完整的SQL以及其他的一些信息最终会存储在BoundSql对象中。
 *
 *
 */
public class BoundSql {

  //一个完整的 SQL 语句，可能会包含问号 ? 占位符
  private final String sql;
  //参数映射列表，SQL 中的每个 #{xxx} 占位符都会被解析成相应的 ParameterMapping 对象
  private final List<ParameterMapping> parameterMappings;
  //运行时参数，即用户传入的参数，比如 Article 对象，或是其他的参数
  private final Object parameterObject;
  //附加参数集合，用于存储一些额外的信息，比如 datebaseId 等
  private final Map<String, Object> additionalParameters;
  //additionalParameters 的元信息对象
  private final MetaObject metaParameters;

  public BoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
    this.sql = sql;
    this.parameterMappings = parameterMappings;
    this.parameterObject = parameterObject;
    this.additionalParameters = new HashMap<String, Object>();
    this.metaParameters = configuration.newMetaObject(additionalParameters);
  }

  public String getSql() {
    return sql;
  }

  public List<ParameterMapping> getParameterMappings() {
    return parameterMappings;
  }

  public Object getParameterObject() {
    return parameterObject;
  }

  public boolean hasAdditionalParameter(String name) {
    String paramName = new PropertyTokenizer(name).getName();
    return additionalParameters.containsKey(paramName);
  }

  public void setAdditionalParameter(String name, Object value) {
    metaParameters.setValue(name, value);
  }

  public Object getAdditionalParameter(String name) {
    return metaParameters.getValue(name);
  }
}
