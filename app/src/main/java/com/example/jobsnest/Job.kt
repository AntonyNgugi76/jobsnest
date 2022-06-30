package com.example.jobsnest

data class Job(
    val acc_name: String,
    val job_desc: String,
    val deadline_date: Int,
    val amt: Int,
    val writer_earning: Int,
    val writer_name: String
)
