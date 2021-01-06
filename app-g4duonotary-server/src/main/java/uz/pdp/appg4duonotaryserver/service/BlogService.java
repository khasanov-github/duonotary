package uz.pdp.appg4duonotaryserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.Attachment;
import uz.pdp.appg4duonotaryserver.entity.Blog;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.BlogDto;
import uz.pdp.appg4duonotaryserver.repository.*;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public ApiResponse saveOrEditBlog(BlogDto blogDto) {

            try {
                Blog blog = new Blog();
                if(blogDto.getId()!=null){
                    blog=blogRepository.findById(blogDto.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("getBlog", "Id", blogDto.getId()));
                }
                blog.setTitle(blogDto.getTitle());
                blog.setDescription(blogDto.getText());
                Attachment attachment = attachmentRepository.findById(blogDto.getPhotoId())
                        .orElseThrow(() -> new ResourceNotFoundException("getAttachment", "Id", blogDto.getPhotoId()));
                blog.setAttachment(attachment);
                blogRepository.save(blog);
                return new ApiResponse(blogDto.getId()!=null?"Edited":"Saved", true);
            }catch (Exception e){
                return new ApiResponse("error", false);
            }

    }

   public ApiResponse getBlog(UUID id){
        try {
            Blog blog = blogRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("getBlog", "id", id));
            return new ApiResponse("ok",true,getBlogDto(blog));
        }catch (Exception e){
            return new ApiResponse("error", false);
        }
   }

    public BlogDto getBlogDto(Blog blog) {
        return new BlogDto(
                blog.getId(),
                blog.getAttachment().getId(),
                blog.getTitle(),
                blog.getDescription()
        );


    }

    public ApiResponse getBlogList(int page, int size) {
        try {
            Page<Blog> blogPage = blogRepository.findAll(CommonUtils.getPageable(page, size));
            return new ApiResponse("Ok",true,blogPage.getContent().stream().map(this::getBlogDto).collect(Collectors.toList()),page,blogPage.getTotalElements());
        } catch (IllegalArgumentException e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }

    public ApiResponse deleteBlog(UUID id) {
        try {
            blogRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        }catch (Exception e){
            return new ApiResponse("Error",false);
        }

    }
}
