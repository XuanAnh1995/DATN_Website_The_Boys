package backend.datn.services;

import backend.datn.dto.request.CollarCreateRequest;
import backend.datn.dto.request.CollarUpdateRequest;
import backend.datn.dto.response.CollarResponse;
import backend.datn.entities.Collar;
import backend.datn.exceptions.EntityAlreadyExistsException;
import backend.datn.exceptions.EntityNotFoundException;
import backend.datn.exceptions.ResourceNotFoundException;
import backend.datn.mapper.CollarMapper;
import backend.datn.repositories.CollarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollarService {
    @Autowired
    CollarRepository collarRepository;

    public Page<CollarResponse> getAllCollars(String search, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Collar> collars = collarRepository.searchCollars(search, pageable);

        return collars.map(CollarMapper::toCollarResponse);
    }

    public CollarResponse getCollarById(Integer id) {
        Collar collar = collarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Color not found with id " + id));
        return CollarMapper.toCollarResponse(collar);
    }

    @Transactional
    public CollarResponse createCollar(CollarCreateRequest collarCreateRequest) {
        if (collarRepository.existsByCollarName(collarCreateRequest.getCollarName())) {
            throw new ResourceNotFoundException("Collar with name " + collarCreateRequest.getCollarName() + " already exists");
        }
        Collar collar = new Collar();
        collar.setCollarName(collarCreateRequest.getCollarName());
        collar.setStatus(collarCreateRequest.getStatus());
        collar = collarRepository.save(collar);
        return CollarMapper.toCollarResponse(collar);
    }

    @Transactional
    public CollarResponse updateCollar(Integer id, CollarUpdateRequest collarUpdateRequest) {
        Collar collar = collarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Collar not found with id " + id));

        if(collar.getCollarName().equalsIgnoreCase(collarUpdateRequest.getName()) && collarRepository.existsByCollarName(collarUpdateRequest.getName())) {
            throw new EntityAlreadyExistsException("Collar with name " + collarUpdateRequest.getName() + " already exists");
        }
        collar.setCollarName(collarUpdateRequest.getName());
        collar.setStatus(collarUpdateRequest.getStatus());
        collar = collarRepository.save(collar);
        return CollarMapper.toCollarResponse(collar);
    }

    @Transactional
    public CollarResponse toggleCollarStatus(Integer id) {
        Collar collar = collarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Collar not found with id " + id));
        collar.setStatus(!collar.getStatus());
        collar = collarRepository.save(collar);
        return CollarMapper.toCollarResponse(collar);
    }

    @Transactional
    public void deleteCollar(Integer id) {
        Collar collar = collarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collar not found with id " + id));
        collarRepository.delete(collar);
    }
}
