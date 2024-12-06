package com.promo.reviewservice.mapper;

import java.util.List;

public interface IModelCollect<F, T> {
    T map(F f);
    List<T> map(List<F> f);
}
