package mocha.client.gfx.sprite;

import com.google.common.collect.Lists;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class SpriteSheetService {

  private Map<String, SpriteSheet> spriteSheetsByPath;

  @PostConstruct
  public void init() {
    spriteSheetsByPath = findAll(getGraphicsDirectory()).stream()
        .collect(toMap(SpriteSheet::getImagePath, Function.identity()));
  }

  private String getGraphicsDirectoryPath() {
    return getGraphicsDirectory().getPath();
  }

  private File getGraphicsDirectory() {
    return getFileForPath("/mocha/gfx");
  }

  private List<SpriteSheet> findAll(File file) {
    if (file.isDirectory()) {
      return Arrays.stream(file.listFiles())
          .map(this::findAll)
          .flatMap(Collection::stream)
          .collect(toList());
    }
    return Lists.newArrayList(getSpriteSheetForPath(file.getPath()));
  }

  SpriteSheet findById(String path) {
    return Optional.ofNullable(spriteSheetsByPath.get(path))
        .orElseGet(() -> getSpriteSheetForPath(getFileForPath(path).getPath()));
  }

  private File getFileForPath(String path) {
    return new File(getClass().getResource(path).getFile());
  }

  private SpriteSheet getSpriteSheetForPath(String path) {
    return new CachingSpriteSheet("file:" + "/" + path, 16);
  }

  SpriteSheet findNext(String path) {
    ArrayList<String> spriteSheetPaths = Lists.newArrayList(spriteSheetsByPath.keySet());
    spriteSheetPaths.sort(String::compareTo);
    int nextSpriteSheetIndex = (spriteSheetPaths.indexOf(path) + 1) % spriteSheetPaths.size();
    String nextSpriteSheetPath = spriteSheetPaths.get(nextSpriteSheetIndex);
    return spriteSheetsByPath.get(nextSpriteSheetPath);
  }
}
