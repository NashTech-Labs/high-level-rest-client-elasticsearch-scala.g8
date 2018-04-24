package com.knoldus.elasticsearch.service

import com.knoldus.elasticsearch.models.request.{ESDocument, UpdateRequestDetails, User}

object ElasticSearchProcessor extends App with EsOperations {

  /**
    * Here we are creating Index for Users in Elastic-search.
    */
  val index_name = "user-index"
  val type_name = "user"
  val user = User("20", "User-1", 20)
  val eSDocument = ESDocument(index_name, type_name, "20", user)

  /**
    * Inserting user data in elastic search by calling 'insert' method of EsOperations.
    */
  insert(eSDocument)

  /**
    * For Updating user data for a particular id by calling 'update' method of EsOperations
    * passing in the field name that needs to be updated.
    *
    * Example:
    * val fieldTobeUpdated = "name"
    * val updatedValue = "User-1-renamed"
    * val updateRequestDetails = UpdateRequestDetails(index_name, type_name, "1", fieldTobeUpdated, updatedValue)
    *
    * update(updateRequestDetails)
    * It will update the User name for id 1.
    */

  /**
    * For Searching all the users in the elastic-search.
    *
    * Example:
    * searchAll[User](index_name, type_name)
    * Returns the list of users in elastic-search.
    */

  /**
    * For Deleting user data for a particular id by calling 'delete' method of EsOperations.
    *
    * Example:
    * delete(index_name, type_name, "1")
    * This will delete user with Id 1.
    */
}

