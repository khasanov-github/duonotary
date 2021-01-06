package uz.pdp.appg4duonotaryserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.pdp.appg4duonotaryserver.service.AttachmentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

//    @PreAuthorize("hasAnyAuthority('UPLOAD_FILE_ATTACHMENT')")
    @PostMapping("/upload")
    public HttpEntity<?> uploadFile(MultipartHttpServletRequest request) {
        UUID uuid = attachmentService.uploadFile(request);
        return ResponseEntity
                .ok(new String[]{
                        ServletUriComponentsBuilder.
                                fromCurrentContextPath()
                                .path("/api/attachment/")
                                .path(uuid.toString())
                                .toUriString()
                });
    }

//    @PreAuthorize("hasAnyAuthority('GET_FILE_ATTACHMENT')")
    @GetMapping("/{id}")
    public HttpEntity<?> getFile(@PathVariable UUID id) {
        return attachmentService.getFile(id);
    }
}
