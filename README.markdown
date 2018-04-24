A [Giter8][g8] template that describes how to use Java High-Level Rest Client to connect with Elasticsearch service running either locally or as AWS service.!

# Java High-Level REST Client Scala

> This is a simple application to understand how we can use high-level REST client in order to store and retrieve data from Elasticsearch.

Steps to follow to clone and compile the application:

- Open the terminal.
- Clone the project ```sbt new knoldus/high-level-rest-client-elasticsearch-scala.g8```.
- ```cd high-level-rest-client-elasticsearch-scala```.
- Execute ```sbt clean compile```.

Steps to follow to run the application:

- Set the configurations in ```application.conf``` if you want to use Amazon ES service. Else run elasticsearch locally.
- Use ```sbt run``` to execute CRUD operations on Elasticsearch as specified in ```ElasticSearchProcessor```.

You can now run the commands on either local Kibana or AWS proxy Kibana to interact with the data stored in indices -

- By executing ```GET /_cat/indices?v```, you can list all the indices. You can see ```user-index``` index is being created.
- By executing ```GET /user-index/_search { "query": { "match_all": {}  }}```, you can list out all the data in users-index.

For more information on Amazon ES, you can check out the following blogs:

- https://blog.knoldus.com/2018/04/15/amazon-es-setting-up-the-cluster-1/
- https://blog.knoldus.com/2018/04/16/amazon-es-secure-your-cluster-from-anonymous-users-2/


Template license
----------------
Written in 2018 by Divya Dua

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.

[g8]: http://www.foundweekends.org/giter8/
