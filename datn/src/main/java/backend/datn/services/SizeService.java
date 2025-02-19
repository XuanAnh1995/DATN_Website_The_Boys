package backend.datn.services;

import backend.datn.dto.request.SizeRequest;
import backend.datn.dto.response.SizeResponse;
import backend.datn.entities.Size;
import backend.datn.exceptions.EntityAlreadyExistsException;
import backend.datn.exceptions.EntityNotFoundException;
import backend.datn.exceptions.ResourceNotFoundException;
import backend.datn.mapper.SizeMapper;
import backend.datn.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SizeService {
    @Autowired
    SizeRepository sizeRepository;

    public Page<SizeResponse> getAllSizes(String search, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Size> sizes = sizeRepository.searchSizes(search, pageable);

        return sizes.map(SizeMapper::toSizeResponse);
    }

    public SizeResponse getSizeById(Integer id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Size not found with id " + id));
        return SizeMapper.toSizeResponse(size);
    }

    @Transactional
    public SizeResponse createSize(SizeRequest sizeRequest) {
        if (sizeRepository.existsBySizeName(sizeRequest.getName())) {
            throw new ResourceNotFoundException("Size with name " + sizeRequest.getName() + " already exists");
        }
        Size size = new Size();
        size.setSizeName(sizeRequest.getName());
        size.setStatus(sizeRequest.getStatus());
        size = sizeRepository.save(size);
        return SizeMapper.toSizeResponse(size);
    }

    @Transactional
    public SizeResponse updateSize(Integer id, SizeRequest sizeRequest) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Size not found with id " + id));

        if(size.getSizeName().equalsIgnoreCase(sizeRequest.getName()) && sizeRepository.existsBySizeName(sizeRequest.getName())) {
            throw new EntityAlreadyExistsException("Size with name " + sizeRequest.getName() + " already exists");
        }
        size.setSizeName(sizeRequest.getName());
        size.setStatus(sizeRequest.getStatus());
        size = sizeRepository.save(size);
        return SizeMapper.toSizeResponse(size);
    }

    @Transactional
    public void deleteSize(Integer id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size not found with id " + id));
        sizeRepository.delete(size);
    }

    @Transactional
    public SizeResponse toggleSizeStatus(Integer id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Size not found with id " + id));
        size.setStatus(!size.getStatus());
        size = sizeRepository.save(size);
        return SizeMapper.toSizeResponse(size);
    }
}
