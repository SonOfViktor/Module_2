package com.epam.esm.controller;

import com.epam.esm.dto.CertificateTagsDto;
import com.epam.esm.entity.SelectQueryParameter;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.GiftCertificateTagDtoService;
import com.epam.esm.validategroup.ForCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.groups.Default;
import java.util.List;

@RestController
@RequestMapping("/certificates")
@Validated
public class GiftCertificateController {
    private GiftCertificateTagDtoService certificateTagService;
    private GiftCertificateService certificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateTagDtoService certificateTagService,
                                     GiftCertificateService certificateService) {
        this.certificateTagService = certificateTagService;
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<CertificateTagsDto> showAllGiftCertificates() {
        List<CertificateTagsDto> allCertificates = certificateTagService.findAllGiftCertificateTagDto();

        return allCertificates;
    }

    @GetMapping("/{id}")
    public CertificateTagsDto showCertificate(@PathVariable @Positive int id) {
        CertificateTagsDto certificate = certificateTagService.findGiftCertificateTagDto(id);

        return certificate;
    }

    @PostMapping("/param")
    public List<CertificateTagsDto> showCertificateWithParameters(@Valid @RequestBody SelectQueryParameter queryParam) {
        List<CertificateTagsDto> certificateWithParameters =
                certificateTagService.findGiftCertificateTagDtoByParam(queryParam);

        return certificateWithParameters;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int addCertificate(@Validated({ForCreate.class, Default.class}) @RequestBody CertificateTagsDto certificateTagsDto) {
        int id = certificateTagService.addGiftCertificateTagDto(certificateTagsDto);

        return id;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCertificate(@Valid @RequestBody CertificateTagsDto certificateTagDto, @PathVariable @Positive int id) {
        certificateTagService.updateGiftCertificateTagDto(certificateTagDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable @Positive int id) {
        certificateService.deleteCertificate(id);
    }
}
