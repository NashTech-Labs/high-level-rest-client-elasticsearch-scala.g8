package com.knoldus.elasticsearch.models.request

/**
  * Case class used to update data.
  * @param index Index name.
  * @param docType Type.
  * @param docId Document ID.
  * @param fieldName Field whose value needs to be updated.
  * @param value Updated Value.
  */
case class UpdateRequestDetails(index: String, docType: String, docId: String, fieldName: String, value: Any)
