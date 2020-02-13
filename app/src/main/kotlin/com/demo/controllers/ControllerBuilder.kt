package com.demo.controllers

import com.demo.controllers.types.BanController
import com.demo.controllers.types.CrudController
import com.demo.controllers.types.LoyaltyController
import com.demo.controllers.types.StaticController
import com.demo.controllers.utils.getErrorResponse
import com.demo.dsl.*
import com.demo.dto.serializers.*
import com.demo.services.BanServiceImpl
import com.demo.services.CrudServiceImpl
import com.demo.services.LoyaltyServiceImpl
import com.demo.services.transformers.AppointmentTransformer
import com.demo.services.transformers.ClientTransformer
import com.demo.services.transformers.PurchaseTransformer
import com.demo.services.transformers.ServiceTransformer
import com.demo.services.utils.*
import spark.Spark.*

object ControllerBuilder {

    private val logger = logger<ControllerBuilder>()

    fun registerRouting() {
        registerStaticRouting()
        registerLoyaltyRouting()
        registerBanController()
        registerClientRouting()
        registerAppointmentRouting()
        registerServiceRouting()
        registerPurchaseRouting()
        registerStatusHandling()
    }

    private fun registerStaticRouting() {
        logger.info("Registering Static Routing")
        StaticController()
    }

    private fun registerLoyaltyRouting() {
        logger.info("Registering Loyalty Routing ...")
        val service = LoyaltyServiceImpl(LoyaltyReportDsl)
        LoyaltyController("loyalty", service)
    }

    private fun registerBanController() {
        logger.info("Registering Ban Routing ...")
        val service = BanServiceImpl(ClientCrudDsl)
        BanController("ban", service)
    }

    private fun registerClientRouting() {
        logger.info("Configure Client Routing ...")
        val service = CrudServiceImpl(clientValidator, ClientCrudDsl, ClientTransformer)
        CrudController("client", service, ClientSerializer)
    }

    private fun registerAppointmentRouting() {
        logger.info("Configure Appointment Routing ...")
        val service = CrudServiceImpl(appointmentValidator, AppointmentCrudDsl, AppointmentTransformer)
        CrudController("appointment", service, AppointmentSerializer)
    }

    private fun registerServiceRouting() {
        logger.info("Configure Service Routing ...")
        val service = CrudServiceImpl(serviceValidator, ServiceCrudDsl, ServiceTransformer)
        CrudController("service", service, ServiceSerializer)
    }

    private fun registerPurchaseRouting() {
        logger.info("Configure Purchase Routing ...")
        val service = CrudServiceImpl(purchaseValidator,PurchaseCrudDsl, PurchaseTransformer)
        CrudController("purchase", service, PurchaseSerializer)
    }

    private fun registerStatusHandling() {
        logger.info("Registering Status Handling options")
        before("/api/*") { _, res ->
            res.type("application/json")
        }

        notFound { _, _ ->
            getErrorResponse("Not Found")
        }

        internalServerError { _, _ ->
            getErrorResponse("Internal Server Error")
        }

        exception(Exception::class.java) { exc, _, res ->
            val msg = exc.message ?: "Unknown Error occurred"
            val dto = getErrorResponse(msg)
            logger.error(msg)

            res.status(400)
            res.body(dto)
        }
    }
}