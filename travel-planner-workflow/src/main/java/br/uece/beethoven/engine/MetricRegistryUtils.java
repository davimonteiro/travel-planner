package br.uece.beethoven.engine;


import com.codahale.metrics.MetricRegistry;


public class MetricRegistryUtils {

    private final MetricRegistry metrics = new MetricRegistry();

    private static class MetricRegistryHolder {
        private static final MetricRegistry INSTANCE = new MetricRegistry();
    }

    public static MetricRegistry getInstance() {
        return MetricRegistryHolder.INSTANCE;
    }

}
