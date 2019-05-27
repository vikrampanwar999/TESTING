package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.websocket.bean.OrderDetailsMap;


@Repository
public interface OrderDetailsMapRepository extends CrudRepository<OrderDetailsMap,Long> {

	
}
