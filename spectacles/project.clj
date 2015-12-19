(defproject spectacles "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Apache License 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [quil "2.3.0"]]


  :resource-paths [
                   ;; Wordmaps / WordCram
                   "resources/wordcram/library/WordCram.jar"
                   "resources/wordcram/library/cue.language.jar"
                   "resources/wordcram/library/jsoup-1.7.2.jar"
                   ]
  )
