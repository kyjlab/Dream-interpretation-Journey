package com.example.dreaminterpretationjourney;

import java.util.*;

public class DreamInterpreter {

    private static final Map<String, String> keywordMeanings = new HashMap<>();
    private static final Set<String> positiveWords = new HashSet<>();
    private static final Set<String> negativeWords = new HashSet<>();

    static {
        // 키워드 해몽 맵
        keywordMeanings.put("물", "물은 감정을 상징하며, 깨끗한 물은 긍정적인 감정, 더러운 물은 불안을 나타냅니다.");
        keywordMeanings.put("불", "불은 정열이나 파괴적인 감정을 의미할 수 있습니다.");
        keywordMeanings.put("하늘", "하늘은 자유로움이나 희망을 의미합니다.");
        keywordMeanings.put("도망", "도망치는 꿈은 스트레스나 회피하고 싶은 감정을 나타냅니다.");
        keywordMeanings.put("죽음", "죽음은 종결과 새로운 시작을 의미할 수 있습니다.");
        keywordMeanings.put("비행", "비행은 자유, 해방감을 상징합니다.");
        keywordMeanings.put("시험", "시험 보는 꿈은 불안, 긴장감과 관련 있습니다.");

        // 감정 단어 목록
        positiveWords.addAll(Arrays.asList("좋다", "행복", "웃다", "기쁘다", "설레다"));
        negativeWords.addAll(Arrays.asList("무섭다", "슬프다", "불안", "화나다", "괴롭다"));
    }

    public static List<String> extractKeywords(String dream) {
        List<String> keywords = new ArrayList<>();
        for (String word : keywordMeanings.keySet()) {
            if (dream.contains(word)) {
                keywords.add(word);
            }
        }
        return keywords;
    }

    public static String analyzeEmotion(String dream) {
        int pos = 0, neg = 0;
        for (String word : positiveWords) {
            if (dream.contains(word)) pos++;
        }
        for (String word : negativeWords) {
            if (dream.contains(word)) neg++;
        }

        if (pos > neg) return "긍정적인 감정 상태로 보입니다.";
        else if (neg > pos) return "부정적인 감정 상태로 보입니다.";
        else return "감정 상태를 명확히 판단하기 어렵습니다.";
    }

    public static String interpret(String dream) {
        List<String> keywords = extractKeywords(dream);
        StringBuilder result = new StringBuilder();

        if (keywords.isEmpty()) {
            result.append("꿈에서 특별한 상징 키워드를 찾을 수 없습니다.\n");
        } else {
            result.append("키워드 기반 해몽 결과:\n");
            for (String keyword : keywords) {
                result.append("- ").append(keyword).append(": ").append(keywordMeanings.get(keyword)).append("\n");
            }
        }

        result.append("\n감정 분석 결과:\n").append(analyzeEmotion(dream));

        return result.toString();
    }
}
