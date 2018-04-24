package com.knoldus.elasticsearch

import com.knoldus.elasticsearch.models.request.{ESDocument, UpdateRequestDetails, User}
import com.knoldus.elasticsearch.service.EsOperations
import org.elasticsearch.action.DocWriteResponse
import org.elasticsearch.action.delete.DeleteResponse
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.update.UpdateResponse
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.search.SearchHits
import org.mockito.ArgumentMatchers
import org.mockito.Mockito._
import org.scalatest.WordSpecLike
import org.scalatest.mockito.MockitoSugar

class EsOperationsSpec extends EsOperations with WordSpecLike with MockitoSugar {

  override val client: RestHighLevelClient = mock[RestHighLevelClient]
  val mockedIndexResponse: IndexResponse = mock[IndexResponse]
  val mockedUpdateResponse: UpdateResponse = mock[UpdateResponse]
  val mockedDeleteResponse: DeleteResponse = mock[DeleteResponse]
  val mockedSearchResponse: SearchResponse = mock[SearchResponse]

  val index_name = "test-user-index"
  val type_name = "test-users"
  val testUser = User("1", "User-1", 20)
  val eSDocument = ESDocument(index_name, type_name, "1", testUser)

  val fieldTobeUpdated = "name"
  val valueForUpdation = "User-1-renamed"
  val updateRequestDetails = UpdateRequestDetails(index_name, type_name, "1", fieldTobeUpdated, valueForUpdation)

  "EsOperations" should {

    "return true for successfully inserting data in elastic-search" in {
      when(client.index(ArgumentMatchers.any())) thenReturn mockedIndexResponse
      when(mockedIndexResponse.getResult) thenReturn DocWriteResponse.Result.CREATED
      assert(insert(eSDocument))
    }

    "return true by updating user data if the user already exists while inserting" in {
      when(client.index(ArgumentMatchers.any())) thenReturn mockedIndexResponse
      when(mockedIndexResponse.getResult) thenReturn DocWriteResponse.Result.UPDATED
      assert(insert(eSDocument))
    }

    "return false if there is some problem while inserting user" in {
      when(client.index(ArgumentMatchers.any())) thenReturn mockedIndexResponse
      when(mockedIndexResponse.getResult) thenReturn DocWriteResponse.Result.NOOP
      assert(!insert(eSDocument))
    }

    "return true after successfully updating user data" in {
      when(client.update(ArgumentMatchers.any())) thenReturn mockedUpdateResponse
      when(mockedUpdateResponse.getResult) thenReturn DocWriteResponse.Result.UPDATED
      assert(update(updateRequestDetails))
    }

    "return false if there is some error while updating user data" in {
      when(client.update(ArgumentMatchers.any())) thenReturn mockedUpdateResponse
      when(mockedUpdateResponse.getResult) thenReturn DocWriteResponse.Result.NOOP
      assert(!update(updateRequestDetails))
    }

    "return true on successful deletion of user data" in {
      when(client.delete(ArgumentMatchers.any())) thenReturn mockedDeleteResponse
      when(mockedDeleteResponse.getResult) thenReturn DocWriteResponse.Result.DELETED
      assert(delete(index_name, type_name, "1"))
    }

    "return false  if deletion is tried for user which does not exist" in {
      when(client.delete(ArgumentMatchers.any())) thenReturn mockedDeleteResponse
      when(mockedDeleteResponse.getResult) thenReturn DocWriteResponse.Result.NOT_FOUND
      assert(!delete(index_name, type_name, "1"))
    }

    "return list of users on executing search query" in {
      val searchHits = new SearchHits(Nil.toArray, 1, 1)
      when(client.search(ArgumentMatchers.any())) thenReturn mockedSearchResponse
      when(mockedSearchResponse.getHits) thenReturn searchHits

      val expectedResult = Nil
      val actualResult = searchAll[User](index_name, type_name)
      assert(actualResult.equals(expectedResult))
    }

  }

}
