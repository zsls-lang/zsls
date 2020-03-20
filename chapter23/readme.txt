创建名为 mydlq-user 的索引与对应 Mapping (mydlq-user->index,doc->type)
动态映射（dynamic：true）：动态添加新的字段（或缺省）。
静态映射（dynamic：false）：忽略新的字段。在原有的映射基础上，当有新的字段时，不会主动的添加新的映射关系，只作为查询结果出现在查询中。
严格模式（dynamic： strict）：如果遇到新的字段，就抛出异常


参考：http://www.mydlq.club/article/64/

PUT /mydlq-user
{
  "mappings": {
    "doc": {
      "dynamic": true,
      "properties": {
        "name": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword"
            }
          }
        },
        "address": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword"
            }
          }
        },
        "remark": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword"
            }
          }
        },
        "age": {
          "type": "integer"
        },
        "salary": {
          "type": "float"
        },
        "birthDate": {
          "type": "date",
          "format": "yyyy-MM-dd"
        },
        "createTime": {
          "type": "date"
        }
      }
    }
  }
}