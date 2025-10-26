package com.example.noticeservice.shared.config.profile

import com.example.noticeservice.shared.enum.ProfileEnum
import com.example.noticeservice.shared.exceptions.IllegalStateExceptionSingleLine
import com.example.noticeservice.shared.helpers.EnumHelper
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class StartupProfileGuard(private val env: Environment) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val activeProfile = env.getProperty("spring.profiles.active")?.trim()
        val profile = ProfileEnum.getEnumByValue(activeProfile)

        if (profile.isUnknown()) {
            val supportedValues = EnumHelper.getAllNamesAsString<ProfileEnum>()
            val message = "❌ Invalid configuration: profile '$activeProfile' is not allowed to start application.\n" +
                    "Supported values are: [$supportedValues]."
            throw IllegalStateExceptionSingleLine(message)
        }

        println("✅ Profile: $profile used in application.yaml")
    }
}

