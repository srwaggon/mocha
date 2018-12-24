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
    spriteSheetsByPath = findAll().stream()
        .collect(toMap(SpriteSheet::getImagePath, Function.identity()));
  }

  private List<SpriteSheet> findAll() {
    return findAll(new File(getClass().getResource("/mocha/gfx").getFile()));
  }

  private List<SpriteSheet> findAll(File file) {
    if (file.isDirectory()) {
      return Arrays.stream(file.listFiles())
          .map(this::findAll)
          .flatMap(Collection::stream)
          .collect(toList());
    }
    return Lists.newArrayList(getSpriteSheetForPath(file.getAbsolutePath()));
  }

  private SpriteSheet getSpriteSheetForPath(String path) {
    return new CachingSpriteSheet("file:" + "/" + path, 16);
  }

  SpriteSheet findById(String path) {
    Optional<SpriteSheet> maybeSpriteSheet = Optional.ofNullable(spriteSheetsByPath.get(path));
    if (maybeSpriteSheet.isPresent()) {
      return maybeSpriteSheet.get();
    }
    SpriteSheet spriteSheet = getSpriteSheetForPath(getClass().getResource(path).getFile());
    spriteSheetsByPath.put(path, spriteSheet);
    return spriteSheet;
  }

  SpriteSheet findNext(String path) {
    ArrayList<String> spriteSheetPaths = Lists.newArrayList(spriteSheetsByPath.keySet());
//    spriteSheetPaths.sort(String::compareTo);
    int nextSpriteSheetIndex = (spriteSheetPaths.indexOf(path) + 1) % spriteSheetPaths.size();
    String nextSpriteSheetPath = spriteSheetPaths.get(nextSpriteSheetIndex);
    return spriteSheetsByPath.get(nextSpriteSheetPath);
  }
}
