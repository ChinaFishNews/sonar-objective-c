/*
 * Sonar Objective-C Plugin
 * Copyright (C) 2012 OCTO Technology, Backelite,
 *             Denis Bregeon, Mete Balci, Andrés Gil Herrera, Matthew DeTullio
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.objectivec.oclint;

import com.google.common.io.Closeables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.plugins.objectivec.ObjectiveC;

import java.io.InputStreamReader;
import java.io.Reader;

public final class OCLintProfile extends ProfileDefinition {
    private static final Logger LOGGER = LoggerFactory.getLogger(OCLintProfile.class);

    private final OCLintProfileImporter importer;

    public OCLintProfile(final OCLintProfileImporter importer) {
        this.importer = importer;
    }

    @Override
    public RulesProfile createProfile(final ValidationMessages messages) {
        LOGGER.info("Creating OCLint Profile");
        Reader profileXmlReader = null;

        try {
            profileXmlReader = new InputStreamReader(OCLintProfile.class.getResourceAsStream(
                    "/org/sonar/plugins/objectivec/profile-oclint.xml"));

            RulesProfile profile = importer.importProfile(profileXmlReader, messages);
            profile.setLanguage(ObjectiveC.KEY);
            profile.setName(OCLintRulesDefinition.REPOSITORY_KEY);
            profile.setDefaultProfile(true);

            return profile;
        } finally {
            Closeables.closeQuietly(profileXmlReader);
        }
    }
}
