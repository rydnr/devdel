class CachingIsNewStrategyFactory
    implements IsNewStrategyFactory, to cache resolved {@link IsNewStrategy} instances per type to avoid re-resolving them on each an every request.
    overrides getIsNewStrategy(Class<?> type) from IsNewStrategyFactory {
        returns the already cached strategy for given type,
        otherwise uses a IsNewStrategyFactory delegate to create a new strategy {
            and therefore we need such delegate to be an instance attribute,
            as well as define a non-default constructor with a IsNewStrategyFactory delegate {
                asserts delegate is not null
                annotates the delegate as an instance attribute {
                    declares the delegate attribute
                }
            }
        }
        caches it,
        and returns it.
    }
}
