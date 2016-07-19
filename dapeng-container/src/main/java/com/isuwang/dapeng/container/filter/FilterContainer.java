package com.isuwang.dapeng.container.filter;

import com.isuwang.dapeng.container.conf.SoaServerFilter;
import com.isuwang.dapeng.container.Container;
import com.isuwang.dapeng.container.ContainerStartup;
import com.isuwang.dapeng.core.filter.Filter;
import com.isuwang.dapeng.core.filter.container.ContainerFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter Container
 *
 * @author craneding
 * @date 16/2/3
 */
public class FilterContainer implements Container {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterContainer.class);

    private static List<Filter> filters = new ArrayList<>();

    @Override
    public void start() {
        try {
            for (SoaServerFilter soaFilter : ContainerStartup.soaServer.getSoaFilters().getSoaServerFilter()) {
                Class filterClass = FilterContainer.class.getClassLoader().loadClass(soaFilter.getRef());
                Filter filter = (Filter) filterClass.newInstance();

                ContainerFilterChain.addFilter(filter);

                LOGGER.info("service load filter {} with path {}", soaFilter.getName(), soaFilter.getRef());

                filters.add(filter);
            }

            filters.stream()
                    .filter(soaFilter -> soaFilter instanceof StatusFilter)
                    .forEach(soaFilter -> {
                        ((StatusFilter) soaFilter).init();
                    });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void stop() {
        filters.stream()
                .filter(soaFilter -> soaFilter instanceof StatusFilter)
                .forEach(soaFilter -> ((StatusFilter) soaFilter).destory());

        filters.forEach(ContainerFilterChain::removeFilter);

        filters.clear();
    }

}
