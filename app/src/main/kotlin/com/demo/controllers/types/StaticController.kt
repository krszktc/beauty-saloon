package com.demo.controllers.types

import spark.Spark.get

class StaticController {
    init {
        get("/") { _, _ ->
            "Hi! I'm Static Content :)"
        }
    }
}