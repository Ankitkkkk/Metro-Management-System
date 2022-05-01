package com.mms.mms.user.domain

import java.util.*

data class FareDetail(val from: String,
                      val to: String,
                      val checkInTime: Date,
                      val checkOutTime: Date,
                      val totalFare: Int)
