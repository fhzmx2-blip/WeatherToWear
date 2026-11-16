
package com.weather.WeatherToWear.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CloudinaryUtil {
    private final Cloudinary cloudinary;
    private static final long MAX_FILE_SIZE = 8 * 1024 * 1024;

    public Map<String, Object> uploadImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("이미지 용량은 8MB를 초과할 수 없습니다.");
        }

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "image"
                )
        );

        Map<String, Object> result = new HashMap<>();

        result.put("url", uploadResult.get("secure_url"));
        result.put("public_id", uploadResult.get("public_id"));
        result.put("size", file.getSize());

        return result;
    }

    public void deleteImage(String publicId) throws IOException {
        if (publicId == null || publicId.isBlank()) {
            return;
        }

        Map result = cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.asMap(
                        "resource_type", "image",
                        "invalidate", true
                )
        );

        if (!"ok".equals(result.get("result"))) {
            throw new IllegalStateException("Cloudinary 이미지 삭제에 실패했습니다.");
        }
    }
}
