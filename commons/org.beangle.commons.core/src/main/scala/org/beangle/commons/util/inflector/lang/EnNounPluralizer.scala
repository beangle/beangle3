/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.util.inflector.lang

object EnNounPluralizer {
  // TODO understand this regex better and compare to Perl!
  val PostfixAdjectiveRegex = "(" + "(?!major|lieutenant|brigadier|adjutant)\\S+(?=(?:-|\\s+)general)|" + "court(?=(?:-|\\s+)martial)" + ")(.*)";

  val Prepositions = Array("about", "above", "across", "after", "among", "around",
    "at", "athwart", "before", "behind", "below", "beneath", "beside", "besides", "between",
    "betwixt", "beyond", "but", "by", "during", "except", "for", "from", "in", "into", "near", "of",
    "off", "on", "onto", "out", "over", "since", "till", "to", "under", "until", "unto", "upon",
    "with");
  val NominativePronouns = Map(
    // nominative reflexive
    "i" -> "we", "myself" -> "ourselves", "you" -> "you", "yourself" -> "yourselves",
    "she" -> "they", "herself" -> "themselves", "he" -> "they", "himself" -> "themselves",
    "it" -> "they", "itself" -> "themselves", "they" -> "they", "themself" -> "themselves",
    // possessive
    "mine" -> "ours", "yours" -> "yours", "hers" -> "theirs", "his" -> "theirs",
    "its" -> "theirs", "theirs" -> "theirs")

  val AccusativePronouns = Map(// accusative reflexive
    "me" -> "us", "myself" -> "ourselves", "you" -> "you",
    "yourself" -> "yourselves", "her" -> "them", "herself" -> "themselves",
    "him" -> "them", "himself" -> "themselves", "it" -> "them",
    "itself" -> "themselves", "them" -> "them", "themself" -> "themselves")

  val IrregularNouns = Map(
    "child" -> "children", "brother" -> "brothers",
    "loaf" -> "loaves", "hoof" -> "hoofs",
    "beef" -> "beefs",
    "money" -> "monies", "mongoose" -> "mongooses", "ox" -> "oxen", "cow" -> "cows",
    "soliloquy" -> "soliloquies", "graffito" -> "graffiti", "prima donna" -> "prima donnas",
    "octopus" -> "octopuses", "genie" -> "genies", "ganglion" -> "ganglions", "trilby" -> "trilbys",
    "turf" -> "turfs", "numen" -> "numina", "atman" -> "atmas", "occiput" -> "occiputs",
    // Words ending in -s
    "corpus" -> "corpuses", "opus" -> "opuses", "genus" -> "genera", "mythos" -> "mythoi", "penis" -> "penises",
    "testis" -> "testes", "atlas" -> "atlases")

  val CategoryUninflectedNouns = Array(
    // Fish and herd animals
    ".*fish", "tuna", "salmon", "mackerel", "trout", "bream", "sea[- ]bass", "carp", "cod",
    "flounder", "whiting", ".*deer", ".*sheep",

    // Nationals ending in -ese
    "Portuguese", "Amoyese", "Borghese", "Congoese", "Faroese", "Foochowese", "Genevese", "Genoese",
    "Gilbertese", "Hottentotese", "Kiplingese", "Kongoese", "Lucchese", "Maltese", "Nankingese",
    "Niasese", "Pekingese", "Piedmontese", "Pistoiese", "Sarawakese", "Shavese", "Vermontese",
    "Wenchowese", "Yengeese", ".*[nrlm]ese",

    // Diseases,Other oddities
    ".*pox", "graffiti", "djinn", ".*measles", "mumps",
    // Words ending in -s
    // Pairs or groups subsumed to a singular
    "breeches", "britches", "clippers", "gallows", "hijinks", "headquarters", "pliers", "scissors",
    "testes", "herpes", "pincers", "shears", "proceedings", "trousers",

    // Unassimilated Latin 4th declension
    "cantus", "coitus", "nexus",
    // Recent imports
    "contretemps", "corps", "debris", ".*ois", "siemens",
    // Others
    "diabetes", "jackanapes", "series", "species", "rabies", "chassis", "innings", "news", "mews")

  val CategoryManMansRule = Array("human", "Alabaman", "Bahaman", "Burman",
    "German", "Hiroshiman", "Liman", "Nakayaman", "Oklahoman", "Panaman", "Selman", "Sonaman",
    "Tacoman", "Yakiman", "Yokohaman", "Yuman")

  val CategoryExIcesRule = Array("codex", "murex", "silex")

  val CategoryIxIcesRule = Array("radix", "helix")

  val CategoryUmARule = Array("bacterium", "agendum", "desideratum", "erratum", "stratum",
    "datum", "ovum", "extremum", "candelabrum")

  val CategoryUsIRule = Array("alumnus", "alveolus", "bacillus", "bronchus", "locus", "nucleus", "stimulus", "meniscus")

  val CategoryOnARule = Array("criterion", "perihelion", "aphelion", "phenomenon",
    "prolegomenon", "noumenon", "organon", "asyndeton", "hyperbaton")

  val CategoryAAeRule = Array("alumna", "alga", "vertebra", "persona")

  val CategoryOOsRule = Array("albino", "archipelago", "armadillo", "commando",
    "crescendo", "fiasco", "ditto", "dynamo", "embryo", "ghetto", "guano", "inferno", "jumbo",
    "lumbago", "magneto", "manifesto", "medico", "octavo", "photo", "pro", "quarto", "canto",
    "lingo", "generalissimo", "stylo", "rhino", "casino", "auto", "macro", "zero",
    "solo", "soprano", "basso", "alto", "contralto", "tempo", "piano", "virtuoso")

  val CategorySingularSRule = Array(".*ss", "acropolis", "aegis", "alias",
    "asbestos", "bathos", "bias", "bronchitis", "bursitis", "caddis", "cannabis", "canvas", "chaos",
    "cosmos", "dais", "digitalis", "epidermis", "ethos", "eyas", "gas", "glottis", "hubris", "ibis",
    "lens", "mantis", "marquis", "metropolis", "pathos", "pelvis", "polis", "rhinoceros",
    "sassafras", "trellis", ".*us", "[A-Z].*es",
    "ephemeris", "iris", "clitoris", "chrysalis", "epididymis",
    // Inflamations
    ".*itis")

}

import org.beangle.commons.util.inflector.rule._
import EnNounPluralizer._
import org.beangle.commons.util.inflector.rule.AbstractRegexReplacementRule
import AbstractRegexReplacementRule._
import org.beangle.commons.util.inflector.RuleBasedPluralizer
import java.util.regex._

class EnNounPluralizer extends RuleBasedPluralizer {
  locale = java.util.Locale.ENGLISH
  // References to Steps are to those in Conway's paper
  rules = List(
    // Blank word
    new RegexReplacementRule("^(\\s)$", "$1"),
    // Nouns that do not inflect in the plural (such as "fish") [Step 2]
    new CategoryInflectionRule(CategoryUninflectedNouns, "-", "-"),

    // Compounds [Step 12]
    new AbstractRegexReplacementRule("(?i)^(?:" + PostfixAdjectiveRegex + ")$") {
      override def replace(m: Matcher): String =
        EnNounPluralizer.this.pluralize(m.group(1)) + m.group(2)
    },

    new AbstractRegexReplacementRule("(?i)(.*?)((?:-|\\s+)(?:" + disjunction(Prepositions) + "|d[eu])(?:-|\\s+))a(?:-|\\s+)(.*)") {
      override def replace(m: Matcher): String = pluralize(m.group(1)) + m.group(2) + pluralize(m.group(3))
    },

    new AbstractRegexReplacementRule("(?i)(.*?)((-|\\s+)(" + disjunction(Prepositions) + "|d[eu])((-|\\s+)(.*))?)") {
      override def replace(m: Matcher): String = pluralize(m.group(1)) + m.group(2)
    },

    // Pronouns [Step 3]
    new IrregularMappingRule(NominativePronouns, "(?i)" + disjunction(NominativePronouns.keySet)),
    new IrregularMappingRule(AccusativePronouns, "(?i)" + disjunction(AccusativePronouns.keySet)),
    new IrregularMappingRule(AccusativePronouns, "(?i)(" + disjunction(Prepositions) + "\\s)" + "(" + disjunction(AccusativePronouns.keySet) + ")") {
      override def replace(m: Matcher): String = m.group(1) + mappings.get(m.group(2).toLowerCase).get
    },

    // Standard irregular plurals (such as "children") [Step 4]
    new IrregularMappingRule(IrregularNouns, "(?i)(.*)\\b" + disjunction(IrregularNouns.keySet) + "$"),
    new CategoryInflectionRule(CategoryManMansRule, "-man", "-mans"),
    new RegexReplacementRule("(?i)(\\S*)(person)$", "$1people"),

    // Families of irregular plurals for common suffixes (such as
    // "-men") [Step 5]
    new SuffixInflectionRule("-man", "-man", "-men"),
    new SuffixInflectionRule("-[lm]ouse", "-ouse", "-ice"),
    new SuffixInflectionRule("-tooth", "-tooth", "-teeth"),
    new SuffixInflectionRule("-goose", "-goose", "-geese"),
    new SuffixInflectionRule("-foot", "-foot", "-feet"),

    // Assimilated irregular plurals [Step 6]
    new SuffixInflectionRule("-ceps", "-", "-"),
    new SuffixInflectionRule("-zoon", "-zoon", "-zoa"),
    new SuffixInflectionRule("-[csx]is", "-is", "-es"),
    new CategoryInflectionRule(CategoryExIcesRule, "-ex", "-ices"),
    new CategoryInflectionRule(CategoryIxIcesRule, "-ix", "-ices"),
    new CategoryInflectionRule(CategoryUmARule, "-um", "-a"),
    new CategoryInflectionRule(CategoryUsIRule, "-us", "-i"),
    new CategoryInflectionRule(CategoryOnARule, "-on", "-a"),
    new CategoryInflectionRule(CategoryAAeRule, "-a", "-ae"),

    // Classical irregular plurals [Step 7]
    // Classical plurals have not been implemented

    // Nouns ending in sibilants (such as "churches") [Step 8]
    new CategoryInflectionRule(CategorySingularSRule, "-s", "-ses"),
    new RegexReplacementRule("^([A-Z].*s)$", "$1es"),
    new SuffixInflectionRule("-[cs]h", "-h", "-hes"),
    new SuffixInflectionRule("-x", "-x", "-xes"),
    new SuffixInflectionRule("-z", "-z", "-zes"),

    // Nouns ending with "-f" or "-fe" take "-ves" in the plural (such
    // as "halves") [Step 9]
    new SuffixInflectionRule("-[aeo]lf", "-f", "-ves"),
    new SuffixInflectionRule("-[^d]eaf", "-f", "-ves"),
    new SuffixInflectionRule("-arf", "-f", "-ves"),
    new SuffixInflectionRule("-[nlw]ife", "-fe", "-ves"),

    // Nouns ending with "-y" [Step 10]
    new SuffixInflectionRule("-[aeiou]y", "-y", "-ys"),
    new RegexReplacementRule("^([A-Z].*y)$", "$1s"),
    new SuffixInflectionRule("-y", "-y", "-ies"),

    // Nouns ending with "-o" [Step 11]
    new CategoryInflectionRule(CategoryOOsRule, "-o", "-os"),
    new SuffixInflectionRule("-[aeiou]o", "-o", "-os"),
    new SuffixInflectionRule("-o", "-o", "-oes"),

    // Default rule: add "s" [Step 13]
    new SuffixInflectionRule("-", "-s"))

  protected override def postProcess(trimmedWord: String, pluralizedWord: String): String =
    if (trimmedWord.matches("^I$")) pluralizedWord else super.postProcess(trimmedWord, pluralizedWord)
}