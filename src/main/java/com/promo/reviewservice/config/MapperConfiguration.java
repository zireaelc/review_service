package com.promo.reviewservice.config;

import com.promo.reviewservice.dto.review.ReviewRequest;
import com.promo.reviewservice.dto.review.ReviewResponse;
import com.promo.reviewservice.dto.subcategory.SubcategoryRequest;
import com.promo.reviewservice.dto.subcategory.SubcategoryResponse;
import com.promo.reviewservice.model.Review;
import com.promo.reviewservice.model.Subcategory;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Subcategory, SubcategoryRequest>() {
            @Override
            protected void configure() {
                map().setCategoryId(source.getCategory().getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<Review, ReviewRequest>() {
            @Override
            protected void configure() {
                map().setSubcategoryId(source.getSubcategory().getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<Subcategory, SubcategoryResponse>() {
            @Override
            protected void configure() {
                map().setCategoryId(source.getCategory().getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<Review, ReviewResponse>() {
            @Override
            protected void configure() {
                map().setSubcategoryId(source.getSubcategory().getId());
            }
        });

        return modelMapper;
    }
}
