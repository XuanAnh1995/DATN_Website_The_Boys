package backend.datn.services;

import backend.datn.dto.request.ColorCreateRequest;
import backend.datn.dto.request.ColorUpdateRequest;
import backend.datn.dto.response.ColorResponse;
import backend.datn.entities.Color;
import backend.datn.exceptions.EntityAlreadyExistsException;
import backend.datn.exceptions.EntityNotFoundException;
import backend.datn.exceptions.ResourceNotFoundException;
import backend.datn.mapper.ColorMapper;
import backend.datn.repositories.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ColorService {
    @Autowired
    ColorRepository colorRepository;

    public Page<ColorResponse> getAllColors(String search, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Color> colors = colorRepository.searchColors(search, pageable);

        return colors.map(ColorMapper::toColorResponse);
    }

    public ColorResponse getColorById(Integer id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Color not found with id " + id));
        return ColorMapper.toColorResponse(color);
    }

    @Transactional
    public ColorResponse createColor(ColorCreateRequest colorCreateRequest) {
        if (colorRepository.existsByColorName(colorCreateRequest.getName())) {
            throw new ResourceNotFoundException("Color with name " + colorCreateRequest.getName() + " already exists");
        }
        Color color = new Color();
        color.setColorName(colorCreateRequest.getName());
        color.setStatus(colorCreateRequest.getStatus());
        color = colorRepository.save(color);
        return ColorMapper.toColorResponse(color);
    }

    @Transactional
    public ColorResponse updateColor(Integer id, ColorUpdateRequest colorUpdateRequest) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Color not found with id " + id));

        if(color.getColorName().equalsIgnoreCase(colorUpdateRequest.getName()) && colorRepository.existsByColorName(colorUpdateRequest.getName())) {
            throw new EntityAlreadyExistsException("Color with name " + colorUpdateRequest.getName() + " already exists");
        }
        color.setColorName(colorUpdateRequest.getName());
        color.setStatus(colorUpdateRequest.getStatus());
        color = colorRepository.save(color);
        return ColorMapper.toColorResponse(color);
    }

    @Transactional
    public ColorResponse toggleColorStatus(Integer id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Color not found with id " + id));
        color.setStatus(!color.getStatus());
        color = colorRepository.save(color);
        return ColorMapper.toColorResponse(color);
    }

    @Transactional
    public void deleteColor(Integer id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color not found with id " + id));
        colorRepository.delete(color);
    }
}
