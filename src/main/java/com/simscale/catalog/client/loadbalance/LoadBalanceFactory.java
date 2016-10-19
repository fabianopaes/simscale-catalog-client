package com.simscale.catalog.client.loadbalance;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.ObjectUtils;

public class LoadBalanceFactory {

    public static LoadBalance getInstance(LoadBalanceInfo info){
        return new LoadBalanceRoundRobin(info);
    }

    public static LoadBalance getInstance(LoadBalanceAlgorithm alg, LoadBalanceInfo info){

        if(ObjectUtils.equals(alg, LoadBalanceAlgorithm.ROUND_ROBIN)){
            return LoadBalanceFactory.getInstance(info);
        }

        throw new IllegalArgumentException(" We only support these following alg: " +
                Joiner.on(",").join(LoadBalanceAlgorithm.values())
        );
    }

}
