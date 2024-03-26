package di

import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoinIos(
    mapProtocol: DGisProtocol
) {
    startKoin{
        modules(
            module {
                single<DGisProtocol> {
                    mapProtocol
                }
            } +
            commonModule() +
            listOf(platformModule())
        )
    }
}