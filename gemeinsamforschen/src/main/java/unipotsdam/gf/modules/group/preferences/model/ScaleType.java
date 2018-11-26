package unipotsdam.gf.modules.group.preferences.model;

public enum ScaleType {
    // groups should be composed of mainly the same in this dimension
    HOMOGENOUS,
    // groups should be composed of differences in this dimension
    HETEROGENOUS,
    // groups should be composed of averages in this dimension, no tilts in one direction
    LEVELED,
    // groups should be composed of exactly one of a certain nomen
    NOMINAL
}
