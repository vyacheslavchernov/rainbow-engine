package ru.vych.resources.registry.entries;

import ru.vych.resources.Resource;

public interface RegistryEntry {
    <T> T load();
}
