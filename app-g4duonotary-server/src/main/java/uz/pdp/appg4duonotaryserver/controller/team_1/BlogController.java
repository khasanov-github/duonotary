package uz.pdp.appg4duonotaryserver.controller.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.BlogDto;
import uz.pdp.appg4duonotaryserver.repository.BlogRepository;
import uz.pdp.appg4duonotaryserver.service.BlogService;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

import java.util.UUID;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    BlogRepository blogRepository;

    @PostMapping
    public HttpEntity<?> saveOrEditBlog(@RequestBody BlogDto blogDto) {
        ApiResponse apiResponse = blogService.saveOrEditBlog(blogDto);
        return ResponseEntity.status(apiResponse.isSuccess()?
                        apiResponse.getMessage().equals("Saved")?201:202:409
                ).body(apiResponse);
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getBlogById(@PathVariable UUID id) {
        ApiResponse apiResponse = blogService.getBlog(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/allByPageable")
    public HttpEntity<?> getBlogList(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return ResponseEntity.ok(blogService.getBlogList(page, size));
    }

    @GetMapping("/remove")
    public HttpEntity<?> deleteBlogById(@RequestParam UUID id) {
        ApiResponse apiResponse = blogService.deleteBlog(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
