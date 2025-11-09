package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.item.ItemCreateRequest;
import com.example.portfolio.DTOs.item.ItemResponse;
import com.example.portfolio.exceptions.ResourceNotFoundException;
import com.example.portfolio.models.Item;
import com.example.portfolio.repository.ItemRepository;
import com.example.portfolio.sevices.serviceInterfaces.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    public ItemServiceImpl(ItemRepository repository) {
        this.repository = repository;
    }


    @Override
    public ItemResponse get(Long id) {
        return repository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(()->new ResourceNotFoundException("Item not found with id: " + id));
    }

    @Override
    public ItemResponse create(ItemCreateRequest itemRequest) {
        Item item=mapDtoToEntity(itemRequest);
        repository.save(item);
        return mapEntityToDto(item);
    }

    @Override
    public ItemResponse update(Long id, ItemCreateRequest itemRequest) {
        Item item=repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Item not found with id: " + id));
        if(itemRequest.getName()!=null){
            item.setName(itemRequest.getName());
        }
        if(itemRequest.getCategory()!=null){
            item.setCategory(itemRequest.getCategory());
        }

        item.setBought(itemRequest.isBought());
        repository.save(item);

        return mapEntityToDto(item);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    private ItemResponse mapEntityToDto(Item item){
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .category(item.getCategory())
                .isBought(item.isBought())
                .build();
    }
    private Item mapDtoToEntity(ItemCreateRequest dto){
        return Item.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .isBought(dto.isBought())
                .build();
    }
}
