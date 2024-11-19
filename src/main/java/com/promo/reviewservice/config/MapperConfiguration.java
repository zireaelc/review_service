package com.promo.reviewservice.config;

import com.promo.reviewservice.dto.CategoryDTO;
import com.promo.reviewservice.dto.ReviewDTO;
import com.promo.reviewservice.dto.SubcategoryDTO;
import com.promo.reviewservice.model.Category;
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

        modelMapper.addMappings(new PropertyMap<Subcategory, SubcategoryDTO>() {
            @Override
            protected void configure() {
                map().setCategoryId(source.getCategory().getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<Review, ReviewDTO>() {
            @Override
            protected void configure() {
                map().setSubcategoryId(source.getSubcategory().getId());
            }
        });

        return modelMapper;
    }
}
