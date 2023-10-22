package com.example.sport_path.services

object ServiceLocator {
    private val services: MutableMap<String, Any> = mutableMapOf()
    fun registerService(key: String, service: Any) {
        services[key] = service
    }
    fun <T> getService(key: String): T? {
        if( key in services.keys)
            return services[key] as? T
        else
            throw java.lang.IllegalArgumentException("unknown service")
    }
}