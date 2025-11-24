package com.example.auctionauto.data

class BidRepo(private val bidDao: BidDao) {

    suspend fun addBid(listingId: Int, email: String) {
        bidDao.insertBid(Bid(listingId = listingId, bidderEmail = email))
    }

    suspend fun getUserBids(email: String) =
        bidDao.getBidsByUser(email)
}