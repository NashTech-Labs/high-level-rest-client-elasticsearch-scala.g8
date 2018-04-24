package com.knoldus.elasticsearch.models.request

/**
  * Case class that is used to insert data in Elastic-search
  * @param index Index name.
  * @param docType Type.
  * @param docId Document ID.
  * @param obj Source or object that you want to insert in Elastic-search.
  * @tparam A Object Type.
  */
case class ESDocument[A](index: String, docType: String, docId: String, obj: A)
