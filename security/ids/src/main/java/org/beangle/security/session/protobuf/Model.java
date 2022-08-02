/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.session.protobuf;

public final class Model {
  private Model() {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public interface AccountOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Account)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    java.lang.String getName();

    /**
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    com.google.protobuf.ByteString
    getNameBytes();

    /**
     * <code>string description = 2;</code>
     *
     * @return The description.
     */
    java.lang.String getDescription();

    /**
     * <code>string description = 2;</code>
     *
     * @return The bytes for description.
     */
    com.google.protobuf.ByteString
    getDescriptionBytes();

    /**
     * <code>int32 categoryId = 3;</code>
     *
     * @return The categoryId.
     */
    int getCategoryId();

    /**
     * <code>string remoteToken = 4;</code>
     *
     * @return The remoteToken.
     */
    java.lang.String getRemoteToken();

    /**
     * <code>string remoteToken = 4;</code>
     *
     * @return The bytes for remoteToken.
     */
    com.google.protobuf.ByteString
    getRemoteTokenBytes();

    /**
     * <code>int32 status = 5;</code>
     *
     * @return The status.
     */
    int getStatus();

    /**
     * <code>repeated string authorities = 6;</code>
     *
     * @return A list containing the authorities.
     */
    java.util.List<java.lang.String>
    getAuthoritiesList();

    /**
     * <code>repeated string authorities = 6;</code>
     *
     * @return The count of authorities.
     */
    int getAuthoritiesCount();

    /**
     * <code>repeated string authorities = 6;</code>
     *
     * @param index The index of the element to return.
     * @return The authorities at the given index.
     */
    java.lang.String getAuthorities(int index);

    /**
     * <code>repeated string authorities = 6;</code>
     *
     * @param index The index of the value to return.
     * @return The bytes of the authorities at the given index.
     */
    com.google.protobuf.ByteString
    getAuthoritiesBytes(int index);

    /**
     * <code>repeated string permissions = 7;</code>
     *
     * @return A list containing the permissions.
     */
    java.util.List<java.lang.String>
    getPermissionsList();

    /**
     * <code>repeated string permissions = 7;</code>
     *
     * @return The count of permissions.
     */
    int getPermissionsCount();

    /**
     * <code>repeated string permissions = 7;</code>
     *
     * @param index The index of the element to return.
     * @return The permissions at the given index.
     */
    java.lang.String getPermissions(int index);

    /**
     * <code>repeated string permissions = 7;</code>
     *
     * @param index The index of the value to return.
     * @return The bytes of the permissions at the given index.
     */
    com.google.protobuf.ByteString
    getPermissionsBytes(int index);

    /**
     * <code>repeated .Profile profiles = 8;</code>
     */
    java.util.List<org.beangle.security.session.protobuf.Model.Profile>
    getProfilesList();

    /**
     * <code>repeated .Profile profiles = 8;</code>
     */
    org.beangle.security.session.protobuf.Model.Profile getProfiles(int index);

    /**
     * <code>repeated .Profile profiles = 8;</code>
     */
    int getProfilesCount();

    /**
     * <code>repeated .Profile profiles = 8;</code>
     */
    java.util.List<? extends org.beangle.security.session.protobuf.Model.ProfileOrBuilder>
    getProfilesOrBuilderList();

    /**
     * <code>repeated .Profile profiles = 8;</code>
     */
    org.beangle.security.session.protobuf.Model.ProfileOrBuilder getProfilesOrBuilder(
        int index);

    /**
     * <code>map&lt;string, string&gt; details = 9;</code>
     */
    int getDetailsCount();

    /**
     * <code>map&lt;string, string&gt; details = 9;</code>
     */
    boolean containsDetails(
        java.lang.String key);

    /**
     * Use {@link #getDetailsMap()} instead.
     */
    @java.lang.Deprecated
    java.util.Map<java.lang.String, java.lang.String>
    getDetails();

    /**
     * <code>map&lt;string, string&gt; details = 9;</code>
     */
    java.util.Map<java.lang.String, java.lang.String>
    getDetailsMap();

    /**
     * <code>map&lt;string, string&gt; details = 9;</code>
     */

    /* nullable */
    java.lang.String getDetailsOrDefault(
        java.lang.String key,
        /* nullable */
        java.lang.String defaultValue);

    /**
     * <code>map&lt;string, string&gt; details = 9;</code>
     */

    java.lang.String getDetailsOrThrow(
        java.lang.String key);
  }

  /**
   * Protobuf type {@code Account}
   */
  public static final class Account extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Account)
      AccountOrBuilder {
    private static final long serialVersionUID = 0L;

    // Use Account.newBuilder() to construct.
    private Account(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private Account() {
      name_ = "";
      description_ = "";
      remoteToken_ = "";
      authorities_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      permissions_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      profiles_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Account();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }

    private Account(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              name_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              description_ = s;
              break;
            }
            case 24: {

              categoryId_ = input.readInt32();
              break;
            }
            case 34: {
              java.lang.String s = input.readStringRequireUtf8();

              remoteToken_ = s;
              break;
            }
            case 40: {

              status_ = input.readInt32();
              break;
            }
            case 50: {
              java.lang.String s = input.readStringRequireUtf8();
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                authorities_ = new com.google.protobuf.LazyStringArrayList();
                mutable_bitField0_ |= 0x00000001;
              }
              authorities_.add(s);
              break;
            }
            case 58: {
              java.lang.String s = input.readStringRequireUtf8();
              if (!((mutable_bitField0_ & 0x00000002) != 0)) {
                permissions_ = new com.google.protobuf.LazyStringArrayList();
                mutable_bitField0_ |= 0x00000002;
              }
              permissions_.add(s);
              break;
            }
            case 66: {
              if (!((mutable_bitField0_ & 0x00000004) != 0)) {
                profiles_ = new java.util.ArrayList<org.beangle.security.session.protobuf.Model.Profile>();
                mutable_bitField0_ |= 0x00000004;
              }
              profiles_.add(
                  input.readMessage(org.beangle.security.session.protobuf.Model.Profile.parser(), extensionRegistry));
              break;
            }
            case 74: {
              if (!((mutable_bitField0_ & 0x00000008) != 0)) {
                details_ = com.google.protobuf.MapField.newMapField(
                    DetailsDefaultEntryHolder.defaultEntry);
                mutable_bitField0_ |= 0x00000008;
              }
              com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
                  details__ = input.readMessage(
                  DetailsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
              details_.getMutableMap().put(
                  details__.getKey(), details__.getValue());
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) != 0)) {
          authorities_ = authorities_.getUnmodifiableView();
        }
        if (((mutable_bitField0_ & 0x00000002) != 0)) {
          permissions_ = permissions_.getUnmodifiableView();
        }
        if (((mutable_bitField0_ & 0x00000004) != 0)) {
          profiles_ = java.util.Collections.unmodifiableList(profiles_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
      return org.beangle.security.session.protobuf.Model.internal_static_Account_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    @java.lang.Override
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 9:
          return internalGetDetails();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
      return org.beangle.security.session.protobuf.Model.internal_static_Account_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.beangle.security.session.protobuf.Model.Account.class, org.beangle.security.session.protobuf.Model.Account.Builder.class);
    }

    public static final int NAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object name_;

    /**
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    @java.lang.Override
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      }
    }

    /**
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
    getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DESCRIPTION_FIELD_NUMBER = 2;
    private volatile java.lang.Object description_;

    /**
     * <code>string description = 2;</code>
     *
     * @return The description.
     */
    @java.lang.Override
    public java.lang.String getDescription() {
      java.lang.Object ref = description_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        description_ = s;
        return s;
      }
    }

    /**
     * <code>string description = 2;</code>
     *
     * @return The bytes for description.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
    getDescriptionBytes() {
      java.lang.Object ref = description_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        description_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CATEGORYID_FIELD_NUMBER = 3;
    private int categoryId_;

    /**
     * <code>int32 categoryId = 3;</code>
     *
     * @return The categoryId.
     */
    @java.lang.Override
    public int getCategoryId() {
      return categoryId_;
    }

    public static final int REMOTETOKEN_FIELD_NUMBER = 4;
    private volatile java.lang.Object remoteToken_;

    /**
     * <code>string remoteToken = 4;</code>
     *
     * @return The remoteToken.
     */
    @java.lang.Override
    public java.lang.String getRemoteToken() {
      java.lang.Object ref = remoteToken_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        remoteToken_ = s;
        return s;
      }
    }

    /**
     * <code>string remoteToken = 4;</code>
     *
     * @return The bytes for remoteToken.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
    getRemoteTokenBytes() {
      java.lang.Object ref = remoteToken_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        remoteToken_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int STATUS_FIELD_NUMBER = 5;
    private int status_;

    /**
     * <code>int32 status = 5;</code>
     *
     * @return The status.
     */
    @java.lang.Override
    public int getStatus() {
      return status_;
    }

    public static final int AUTHORITIES_FIELD_NUMBER = 6;
    private com.google.protobuf.LazyStringList authorities_;

    /**
     * <code>repeated string authorities = 6;</code>
     *
     * @return A list containing the authorities.
     */
    public com.google.protobuf.ProtocolStringList
    getAuthoritiesList() {
      return authorities_;
    }

    /**
     * <code>repeated string authorities = 6;</code>
     *
     * @return The count of authorities.
     */
    public int getAuthoritiesCount() {
      return authorities_.size();
    }

    /**
     * <code>repeated string authorities = 6;</code>
     *
     * @param index The index of the element to return.
     * @return The authorities at the given index.
     */
    public java.lang.String getAuthorities(int index) {
      return authorities_.get(index);
    }

    /**
     * <code>repeated string authorities = 6;</code>
     *
     * @param index The index of the value to return.
     * @return The bytes of the authorities at the given index.
     */
    public com.google.protobuf.ByteString
    getAuthoritiesBytes(int index) {
      return authorities_.getByteString(index);
    }

    public static final int PERMISSIONS_FIELD_NUMBER = 7;
    private com.google.protobuf.LazyStringList permissions_;

    /**
     * <code>repeated string permissions = 7;</code>
     *
     * @return A list containing the permissions.
     */
    public com.google.protobuf.ProtocolStringList
    getPermissionsList() {
      return permissions_;
    }

    /**
     * <code>repeated string permissions = 7;</code>
     *
     * @return The count of permissions.
     */
    public int getPermissionsCount() {
      return permissions_.size();
    }

    /**
     * <code>repeated string permissions = 7;</code>
     *
     * @param index The index of the element to return.
     * @return The permissions at the given index.
     */
    public java.lang.String getPermissions(int index) {
      return permissions_.get(index);
    }

    /**
     * <code>repeated string permissions = 7;</code>
     *
     * @param index The index of the value to return.
     * @return The bytes of the permissions at the given index.
     */
    public com.google.protobuf.ByteString
    getPermissionsBytes(int index) {
      return permissions_.getByteString(index);
    }

    public static final int PROFILES_FIELD_NUMBER = 8;
    private java.util.List<org.beangle.security.session.protobuf.Model.Profile> profiles_;

    /**
     * <code>repeated .Profile profiles = 8;</code>
     */
    @java.lang.Override
    public java.util.List<org.beangle.security.session.protobuf.Model.Profile> getProfilesList() {
      return profiles_;
    }

    /**
     * <code>repeated .Profile profiles = 8;</code>
     */
    @java.lang.Override
    public java.util.List<? extends org.beangle.security.session.protobuf.Model.ProfileOrBuilder>
    getProfilesOrBuilderList() {
      return profiles_;
    }

    /**
     * <code>repeated .Profile profiles = 8;</code>
     */
    @java.lang.Override
    public int getProfilesCount() {
      return profiles_.size();
    }

    /**
     * <code>repeated .Profile profiles = 8;</code>
     */
    @java.lang.Override
    public org.beangle.security.session.protobuf.Model.Profile getProfiles(int index) {
      return profiles_.get(index);
    }

    /**
     * <code>repeated .Profile profiles = 8;</code>
     */
    @java.lang.Override
    public org.beangle.security.session.protobuf.Model.ProfileOrBuilder getProfilesOrBuilder(
        int index) {
      return profiles_.get(index);
    }

    public static final int DETAILS_FIELD_NUMBER = 9;

    private static final class DetailsDefaultEntryHolder {
      static final com.google.protobuf.MapEntry<
          java.lang.String, java.lang.String> defaultEntry =
          com.google.protobuf.MapEntry
              .<java.lang.String, java.lang.String>newDefaultInstance(
                  org.beangle.security.session.protobuf.Model.internal_static_Account_DetailsEntry_descriptor,
                  com.google.protobuf.WireFormat.FieldType.STRING,
                  "",
                  com.google.protobuf.WireFormat.FieldType.STRING,
                  "");
    }

    private com.google.protobuf.MapField<
        java.lang.String, java.lang.String> details_;

    private com.google.protobuf.MapField<java.lang.String, java.lang.String>
    internalGetDetails() {
      if (details_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            DetailsDefaultEntryHolder.defaultEntry);
      }
      return details_;
    }

    public int getDetailsCount() {
      return internalGetDetails().getMap().size();
    }

    /**
     * <code>map&lt;string, string&gt; details = 9;</code>
     */

    @java.lang.Override
    public boolean containsDetails(
        java.lang.String key) {
      if (key == null) {
        throw new NullPointerException("map key");
      }
      return internalGetDetails().getMap().containsKey(key);
    }

    /**
     * Use {@link #getDetailsMap()} instead.
     */
    @java.lang.Override
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String> getDetails() {
      return getDetailsMap();
    }

    /**
     * <code>map&lt;string, string&gt; details = 9;</code>
     */
    @java.lang.Override

    public java.util.Map<java.lang.String, java.lang.String> getDetailsMap() {
      return internalGetDetails().getMap();
    }

    /**
     * <code>map&lt;string, string&gt; details = 9;</code>
     */
    @java.lang.Override

    public java.lang.String getDetailsOrDefault(
        java.lang.String key,
        java.lang.String defaultValue) {
      if (key == null) {
        throw new NullPointerException("map key");
      }
      java.util.Map<java.lang.String, java.lang.String> map =
          internalGetDetails().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    /**
     * <code>map&lt;string, string&gt; details = 9;</code>
     */
    @java.lang.Override

    public java.lang.String getDetailsOrThrow(
        java.lang.String key) {
      if (key == null) {
        throw new NullPointerException("map key");
      }
      java.util.Map<java.lang.String, java.lang.String> map =
          internalGetDetails().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    private byte memoizedIsInitialized = -1;

    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(description_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, description_);
      }
      if (categoryId_ != 0) {
        output.writeInt32(3, categoryId_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(remoteToken_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, remoteToken_);
      }
      if (status_ != 0) {
        output.writeInt32(5, status_);
      }
      for (int i = 0; i < authorities_.size(); i++) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, authorities_.getRaw(i));
      }
      for (int i = 0; i < permissions_.size(); i++) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 7, permissions_.getRaw(i));
      }
      for (int i = 0; i < profiles_.size(); i++) {
        output.writeMessage(8, profiles_.get(i));
      }
      com.google.protobuf.GeneratedMessageV3
          .serializeStringMapTo(
              output,
              internalGetDetails(),
              DetailsDefaultEntryHolder.defaultEntry,
              9);
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(description_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, description_);
      }
      if (categoryId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
            .computeInt32Size(3, categoryId_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(remoteToken_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, remoteToken_);
      }
      if (status_ != 0) {
        size += com.google.protobuf.CodedOutputStream
            .computeInt32Size(5, status_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < authorities_.size(); i++) {
          dataSize += computeStringSizeNoTag(authorities_.getRaw(i));
        }
        size += dataSize;
        size += 1 * getAuthoritiesList().size();
      }
      {
        int dataSize = 0;
        for (int i = 0; i < permissions_.size(); i++) {
          dataSize += computeStringSizeNoTag(permissions_.getRaw(i));
        }
        size += dataSize;
        size += 1 * getPermissionsList().size();
      }
      for (int i = 0; i < profiles_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
            .computeMessageSize(8, profiles_.get(i));
      }
      for (java.util.Map.Entry<java.lang.String, java.lang.String> entry
          : internalGetDetails().getMap().entrySet()) {
        com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
            details__ = DetailsDefaultEntryHolder.defaultEntry.newBuilderForType()
            .setKey(entry.getKey())
            .setValue(entry.getValue())
            .build();
        size += com.google.protobuf.CodedOutputStream
            .computeMessageSize(9, details__);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof org.beangle.security.session.protobuf.Model.Account)) {
        return super.equals(obj);
      }
      org.beangle.security.session.protobuf.Model.Account other = (org.beangle.security.session.protobuf.Model.Account) obj;

      if (!getName()
          .equals(other.getName())) return false;
      if (!getDescription()
          .equals(other.getDescription())) return false;
      if (getCategoryId()
          != other.getCategoryId()) return false;
      if (!getRemoteToken()
          .equals(other.getRemoteToken())) return false;
      if (getStatus()
          != other.getStatus()) return false;
      if (!getAuthoritiesList()
          .equals(other.getAuthoritiesList())) return false;
      if (!getPermissionsList()
          .equals(other.getPermissionsList())) return false;
      if (!getProfilesList()
          .equals(other.getProfilesList())) return false;
      if (!internalGetDetails().equals(
          other.internalGetDetails())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
      hash = (37 * hash) + DESCRIPTION_FIELD_NUMBER;
      hash = (53 * hash) + getDescription().hashCode();
      hash = (37 * hash) + CATEGORYID_FIELD_NUMBER;
      hash = (53 * hash) + getCategoryId();
      hash = (37 * hash) + REMOTETOKEN_FIELD_NUMBER;
      hash = (53 * hash) + getRemoteToken().hashCode();
      hash = (37 * hash) + STATUS_FIELD_NUMBER;
      hash = (53 * hash) + getStatus();
      if (getAuthoritiesCount() > 0) {
        hash = (37 * hash) + AUTHORITIES_FIELD_NUMBER;
        hash = (53 * hash) + getAuthoritiesList().hashCode();
      }
      if (getPermissionsCount() > 0) {
        hash = (37 * hash) + PERMISSIONS_FIELD_NUMBER;
        hash = (53 * hash) + getPermissionsList().hashCode();
      }
      if (getProfilesCount() > 0) {
        hash = (37 * hash) + PROFILES_FIELD_NUMBER;
        hash = (53 * hash) + getProfilesList().hashCode();
      }
      if (!internalGetDetails().getMap().isEmpty()) {
        hash = (37 * hash) + DETAILS_FIELD_NUMBER;
        hash = (53 * hash) + internalGetDetails().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static org.beangle.security.session.protobuf.Model.Account parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Account parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Account parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Account parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Account parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Account parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Account parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Account parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Account parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Account parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Account parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Account parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(org.beangle.security.session.protobuf.Model.Account prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }

    /**
     * Protobuf type {@code Account}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Account)
        org.beangle.security.session.protobuf.Model.AccountOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
        return org.beangle.security.session.protobuf.Model.internal_static_Account_descriptor;
      }

      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMapField(
          int number) {
        switch (number) {
          case 9:
            return internalGetDetails();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }

      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMutableMapField(
          int number) {
        switch (number) {
          case 9:
            return internalGetMutableDetails();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
        return org.beangle.security.session.protobuf.Model.internal_static_Account_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                org.beangle.security.session.protobuf.Model.Account.class, org.beangle.security.session.protobuf.Model.Account.Builder.class);
      }

      // Construct using org.beangle.security.session.protobuf.Model.Account.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
            .alwaysUseFieldBuilders) {
          getProfilesFieldBuilder();
        }
      }

      @java.lang.Override
      public Builder clear() {
        super.clear();
        name_ = "";

        description_ = "";

        categoryId_ = 0;

        remoteToken_ = "";

        status_ = 0;

        authorities_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        permissions_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000002);
        if (profilesBuilder_ == null) {
          profiles_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000004);
        } else {
          profilesBuilder_.clear();
        }
        internalGetMutableDetails().clear();
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
      getDescriptorForType() {
        return org.beangle.security.session.protobuf.Model.internal_static_Account_descriptor;
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Account getDefaultInstanceForType() {
        return org.beangle.security.session.protobuf.Model.Account.getDefaultInstance();
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Account build() {
        org.beangle.security.session.protobuf.Model.Account result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Account buildPartial() {
        org.beangle.security.session.protobuf.Model.Account result = new org.beangle.security.session.protobuf.Model.Account(this);
        int from_bitField0_ = bitField0_;
        result.name_ = name_;
        result.description_ = description_;
        result.categoryId_ = categoryId_;
        result.remoteToken_ = remoteToken_;
        result.status_ = status_;
        if (((bitField0_ & 0x00000001) != 0)) {
          authorities_ = authorities_.getUnmodifiableView();
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.authorities_ = authorities_;
        if (((bitField0_ & 0x00000002) != 0)) {
          permissions_ = permissions_.getUnmodifiableView();
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.permissions_ = permissions_;
        if (profilesBuilder_ == null) {
          if (((bitField0_ & 0x00000004) != 0)) {
            profiles_ = java.util.Collections.unmodifiableList(profiles_);
            bitField0_ = (bitField0_ & ~0x00000004);
          }
          result.profiles_ = profiles_;
        } else {
          result.profiles_ = profilesBuilder_.build();
        }
        result.details_ = internalGetDetails();
        result.details_.makeImmutable();
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }

      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }

      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }

      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }

      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }

      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }

      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.beangle.security.session.protobuf.Model.Account) {
          return mergeFrom((org.beangle.security.session.protobuf.Model.Account) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(org.beangle.security.session.protobuf.Model.Account other) {
        if (other == org.beangle.security.session.protobuf.Model.Account.getDefaultInstance()) return this;
        if (!other.getName().isEmpty()) {
          name_ = other.name_;
          onChanged();
        }
        if (!other.getDescription().isEmpty()) {
          description_ = other.description_;
          onChanged();
        }
        if (other.getCategoryId() != 0) {
          setCategoryId(other.getCategoryId());
        }
        if (!other.getRemoteToken().isEmpty()) {
          remoteToken_ = other.remoteToken_;
          onChanged();
        }
        if (other.getStatus() != 0) {
          setStatus(other.getStatus());
        }
        if (!other.authorities_.isEmpty()) {
          if (authorities_.isEmpty()) {
            authorities_ = other.authorities_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureAuthoritiesIsMutable();
            authorities_.addAll(other.authorities_);
          }
          onChanged();
        }
        if (!other.permissions_.isEmpty()) {
          if (permissions_.isEmpty()) {
            permissions_ = other.permissions_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensurePermissionsIsMutable();
            permissions_.addAll(other.permissions_);
          }
          onChanged();
        }
        if (profilesBuilder_ == null) {
          if (!other.profiles_.isEmpty()) {
            if (profiles_.isEmpty()) {
              profiles_ = other.profiles_;
              bitField0_ = (bitField0_ & ~0x00000004);
            } else {
              ensureProfilesIsMutable();
              profiles_.addAll(other.profiles_);
            }
            onChanged();
          }
        } else {
          if (!other.profiles_.isEmpty()) {
            if (profilesBuilder_.isEmpty()) {
              profilesBuilder_.dispose();
              profilesBuilder_ = null;
              profiles_ = other.profiles_;
              bitField0_ = (bitField0_ & ~0x00000004);
              profilesBuilder_ =
                  com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                      getProfilesFieldBuilder() : null;
            } else {
              profilesBuilder_.addAllMessages(other.profiles_);
            }
          }
        }
        internalGetMutableDetails().mergeFrom(
            other.internalGetDetails());
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        org.beangle.security.session.protobuf.Model.Account parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (org.beangle.security.session.protobuf.Model.Account) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int bitField0_;

      private java.lang.Object name_ = "";

      /**
       * <code>string name = 1;</code>
       *
       * @return The name.
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          name_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }

      /**
       * <code>string name = 1;</code>
       *
       * @return The bytes for name.
       */
      public com.google.protobuf.ByteString
      getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      /**
       * <code>string name = 1;</code>
       *
       * @param value The name to set.
       * @return This builder for chaining.
       */
      public Builder setName(
          java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        name_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>string name = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearName() {

        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }

      /**
       * <code>string name = 1;</code>
       *
       * @param value The bytes for name to set.
       * @return This builder for chaining.
       */
      public Builder setNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        name_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object description_ = "";

      /**
       * <code>string description = 2;</code>
       *
       * @return The description.
       */
      public java.lang.String getDescription() {
        java.lang.Object ref = description_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          description_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }

      /**
       * <code>string description = 2;</code>
       *
       * @return The bytes for description.
       */
      public com.google.protobuf.ByteString
      getDescriptionBytes() {
        java.lang.Object ref = description_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          description_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      /**
       * <code>string description = 2;</code>
       *
       * @param value The description to set.
       * @return This builder for chaining.
       */
      public Builder setDescription(
          java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        description_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>string description = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDescription() {

        description_ = getDefaultInstance().getDescription();
        onChanged();
        return this;
      }

      /**
       * <code>string description = 2;</code>
       *
       * @param value The bytes for description to set.
       * @return This builder for chaining.
       */
      public Builder setDescriptionBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        description_ = value;
        onChanged();
        return this;
      }

      private int categoryId_;

      /**
       * <code>int32 categoryId = 3;</code>
       *
       * @return The categoryId.
       */
      @java.lang.Override
      public int getCategoryId() {
        return categoryId_;
      }

      /**
       * <code>int32 categoryId = 3;</code>
       *
       * @param value The categoryId to set.
       * @return This builder for chaining.
       */
      public Builder setCategoryId(int value) {

        categoryId_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>int32 categoryId = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCategoryId() {

        categoryId_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object remoteToken_ = "";

      /**
       * <code>string remoteToken = 4;</code>
       *
       * @return The remoteToken.
       */
      public java.lang.String getRemoteToken() {
        java.lang.Object ref = remoteToken_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          remoteToken_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }

      /**
       * <code>string remoteToken = 4;</code>
       *
       * @return The bytes for remoteToken.
       */
      public com.google.protobuf.ByteString
      getRemoteTokenBytes() {
        java.lang.Object ref = remoteToken_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          remoteToken_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      /**
       * <code>string remoteToken = 4;</code>
       *
       * @param value The remoteToken to set.
       * @return This builder for chaining.
       */
      public Builder setRemoteToken(
          java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        remoteToken_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>string remoteToken = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearRemoteToken() {

        remoteToken_ = getDefaultInstance().getRemoteToken();
        onChanged();
        return this;
      }

      /**
       * <code>string remoteToken = 4;</code>
       *
       * @param value The bytes for remoteToken to set.
       * @return This builder for chaining.
       */
      public Builder setRemoteTokenBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        remoteToken_ = value;
        onChanged();
        return this;
      }

      private int status_;

      /**
       * <code>int32 status = 5;</code>
       *
       * @return The status.
       */
      @java.lang.Override
      public int getStatus() {
        return status_;
      }

      /**
       * <code>int32 status = 5;</code>
       *
       * @param value The status to set.
       * @return This builder for chaining.
       */
      public Builder setStatus(int value) {

        status_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>int32 status = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearStatus() {

        status_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.LazyStringList authorities_ = com.google.protobuf.LazyStringArrayList.EMPTY;

      private void ensureAuthoritiesIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          authorities_ = new com.google.protobuf.LazyStringArrayList(authorities_);
          bitField0_ |= 0x00000001;
        }
      }

      /**
       * <code>repeated string authorities = 6;</code>
       *
       * @return A list containing the authorities.
       */
      public com.google.protobuf.ProtocolStringList
      getAuthoritiesList() {
        return authorities_.getUnmodifiableView();
      }

      /**
       * <code>repeated string authorities = 6;</code>
       *
       * @return The count of authorities.
       */
      public int getAuthoritiesCount() {
        return authorities_.size();
      }

      /**
       * <code>repeated string authorities = 6;</code>
       *
       * @param index The index of the element to return.
       * @return The authorities at the given index.
       */
      public java.lang.String getAuthorities(int index) {
        return authorities_.get(index);
      }

      /**
       * <code>repeated string authorities = 6;</code>
       *
       * @param index The index of the value to return.
       * @return The bytes of the authorities at the given index.
       */
      public com.google.protobuf.ByteString
      getAuthoritiesBytes(int index) {
        return authorities_.getByteString(index);
      }

      /**
       * <code>repeated string authorities = 6;</code>
       *
       * @param index The index to set the value at.
       * @param value The authorities to set.
       * @return This builder for chaining.
       */
      public Builder setAuthorities(
          int index, java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureAuthoritiesIsMutable();
        authorities_.set(index, value);
        onChanged();
        return this;
      }

      /**
       * <code>repeated string authorities = 6;</code>
       *
       * @param value The authorities to add.
       * @return This builder for chaining.
       */
      public Builder addAuthorities(
          java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureAuthoritiesIsMutable();
        authorities_.add(value);
        onChanged();
        return this;
      }

      /**
       * <code>repeated string authorities = 6;</code>
       *
       * @param values The authorities to add.
       * @return This builder for chaining.
       */
      public Builder addAllAuthorities(
          java.lang.Iterable<java.lang.String> values) {
        ensureAuthoritiesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, authorities_);
        onChanged();
        return this;
      }

      /**
       * <code>repeated string authorities = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearAuthorities() {
        authorities_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }

      /**
       * <code>repeated string authorities = 6;</code>
       *
       * @param value The bytes of the authorities to add.
       * @return This builder for chaining.
       */
      public Builder addAuthoritiesBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);
        ensureAuthoritiesIsMutable();
        authorities_.add(value);
        onChanged();
        return this;
      }

      private com.google.protobuf.LazyStringList permissions_ = com.google.protobuf.LazyStringArrayList.EMPTY;

      private void ensurePermissionsIsMutable() {
        if (!((bitField0_ & 0x00000002) != 0)) {
          permissions_ = new com.google.protobuf.LazyStringArrayList(permissions_);
          bitField0_ |= 0x00000002;
        }
      }

      /**
       * <code>repeated string permissions = 7;</code>
       *
       * @return A list containing the permissions.
       */
      public com.google.protobuf.ProtocolStringList
      getPermissionsList() {
        return permissions_.getUnmodifiableView();
      }

      /**
       * <code>repeated string permissions = 7;</code>
       *
       * @return The count of permissions.
       */
      public int getPermissionsCount() {
        return permissions_.size();
      }

      /**
       * <code>repeated string permissions = 7;</code>
       *
       * @param index The index of the element to return.
       * @return The permissions at the given index.
       */
      public java.lang.String getPermissions(int index) {
        return permissions_.get(index);
      }

      /**
       * <code>repeated string permissions = 7;</code>
       *
       * @param index The index of the value to return.
       * @return The bytes of the permissions at the given index.
       */
      public com.google.protobuf.ByteString
      getPermissionsBytes(int index) {
        return permissions_.getByteString(index);
      }

      /**
       * <code>repeated string permissions = 7;</code>
       *
       * @param index The index to set the value at.
       * @param value The permissions to set.
       * @return This builder for chaining.
       */
      public Builder setPermissions(
          int index, java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePermissionsIsMutable();
        permissions_.set(index, value);
        onChanged();
        return this;
      }

      /**
       * <code>repeated string permissions = 7;</code>
       *
       * @param value The permissions to add.
       * @return This builder for chaining.
       */
      public Builder addPermissions(
          java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePermissionsIsMutable();
        permissions_.add(value);
        onChanged();
        return this;
      }

      /**
       * <code>repeated string permissions = 7;</code>
       *
       * @param values The permissions to add.
       * @return This builder for chaining.
       */
      public Builder addAllPermissions(
          java.lang.Iterable<java.lang.String> values) {
        ensurePermissionsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, permissions_);
        onChanged();
        return this;
      }

      /**
       * <code>repeated string permissions = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPermissions() {
        permissions_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
        return this;
      }

      /**
       * <code>repeated string permissions = 7;</code>
       *
       * @param value The bytes of the permissions to add.
       * @return This builder for chaining.
       */
      public Builder addPermissionsBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);
        ensurePermissionsIsMutable();
        permissions_.add(value);
        onChanged();
        return this;
      }

      private java.util.List<org.beangle.security.session.protobuf.Model.Profile> profiles_ =
          java.util.Collections.emptyList();

      private void ensureProfilesIsMutable() {
        if (!((bitField0_ & 0x00000004) != 0)) {
          profiles_ = new java.util.ArrayList<org.beangle.security.session.protobuf.Model.Profile>(profiles_);
          bitField0_ |= 0x00000004;
        }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
          org.beangle.security.session.protobuf.Model.Profile, org.beangle.security.session.protobuf.Model.Profile.Builder, org.beangle.security.session.protobuf.Model.ProfileOrBuilder> profilesBuilder_;

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public java.util.List<org.beangle.security.session.protobuf.Model.Profile> getProfilesList() {
        if (profilesBuilder_ == null) {
          return java.util.Collections.unmodifiableList(profiles_);
        } else {
          return profilesBuilder_.getMessageList();
        }
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public int getProfilesCount() {
        if (profilesBuilder_ == null) {
          return profiles_.size();
        } else {
          return profilesBuilder_.getCount();
        }
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public org.beangle.security.session.protobuf.Model.Profile getProfiles(int index) {
        if (profilesBuilder_ == null) {
          return profiles_.get(index);
        } else {
          return profilesBuilder_.getMessage(index);
        }
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public Builder setProfiles(
          int index, org.beangle.security.session.protobuf.Model.Profile value) {
        if (profilesBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureProfilesIsMutable();
          profiles_.set(index, value);
          onChanged();
        } else {
          profilesBuilder_.setMessage(index, value);
        }
        return this;
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public Builder setProfiles(
          int index, org.beangle.security.session.protobuf.Model.Profile.Builder builderForValue) {
        if (profilesBuilder_ == null) {
          ensureProfilesIsMutable();
          profiles_.set(index, builderForValue.build());
          onChanged();
        } else {
          profilesBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public Builder addProfiles(org.beangle.security.session.protobuf.Model.Profile value) {
        if (profilesBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureProfilesIsMutable();
          profiles_.add(value);
          onChanged();
        } else {
          profilesBuilder_.addMessage(value);
        }
        return this;
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public Builder addProfiles(
          int index, org.beangle.security.session.protobuf.Model.Profile value) {
        if (profilesBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureProfilesIsMutable();
          profiles_.add(index, value);
          onChanged();
        } else {
          profilesBuilder_.addMessage(index, value);
        }
        return this;
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public Builder addProfiles(
          org.beangle.security.session.protobuf.Model.Profile.Builder builderForValue) {
        if (profilesBuilder_ == null) {
          ensureProfilesIsMutable();
          profiles_.add(builderForValue.build());
          onChanged();
        } else {
          profilesBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public Builder addProfiles(
          int index, org.beangle.security.session.protobuf.Model.Profile.Builder builderForValue) {
        if (profilesBuilder_ == null) {
          ensureProfilesIsMutable();
          profiles_.add(index, builderForValue.build());
          onChanged();
        } else {
          profilesBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public Builder addAllProfiles(
          java.lang.Iterable<? extends org.beangle.security.session.protobuf.Model.Profile> values) {
        if (profilesBuilder_ == null) {
          ensureProfilesIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(
              values, profiles_);
          onChanged();
        } else {
          profilesBuilder_.addAllMessages(values);
        }
        return this;
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public Builder clearProfiles() {
        if (profilesBuilder_ == null) {
          profiles_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000004);
          onChanged();
        } else {
          profilesBuilder_.clear();
        }
        return this;
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public Builder removeProfiles(int index) {
        if (profilesBuilder_ == null) {
          ensureProfilesIsMutable();
          profiles_.remove(index);
          onChanged();
        } else {
          profilesBuilder_.remove(index);
        }
        return this;
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public org.beangle.security.session.protobuf.Model.Profile.Builder getProfilesBuilder(
          int index) {
        return getProfilesFieldBuilder().getBuilder(index);
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public org.beangle.security.session.protobuf.Model.ProfileOrBuilder getProfilesOrBuilder(
          int index) {
        if (profilesBuilder_ == null) {
          return profiles_.get(index);
        } else {
          return profilesBuilder_.getMessageOrBuilder(index);
        }
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public java.util.List<? extends org.beangle.security.session.protobuf.Model.ProfileOrBuilder>
      getProfilesOrBuilderList() {
        if (profilesBuilder_ != null) {
          return profilesBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(profiles_);
        }
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public org.beangle.security.session.protobuf.Model.Profile.Builder addProfilesBuilder() {
        return getProfilesFieldBuilder().addBuilder(
            org.beangle.security.session.protobuf.Model.Profile.getDefaultInstance());
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public org.beangle.security.session.protobuf.Model.Profile.Builder addProfilesBuilder(
          int index) {
        return getProfilesFieldBuilder().addBuilder(
            index, org.beangle.security.session.protobuf.Model.Profile.getDefaultInstance());
      }

      /**
       * <code>repeated .Profile profiles = 8;</code>
       */
      public java.util.List<org.beangle.security.session.protobuf.Model.Profile.Builder>
      getProfilesBuilderList() {
        return getProfilesFieldBuilder().getBuilderList();
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
          org.beangle.security.session.protobuf.Model.Profile, org.beangle.security.session.protobuf.Model.Profile.Builder, org.beangle.security.session.protobuf.Model.ProfileOrBuilder>
      getProfilesFieldBuilder() {
        if (profilesBuilder_ == null) {
          profilesBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
              org.beangle.security.session.protobuf.Model.Profile, org.beangle.security.session.protobuf.Model.Profile.Builder, org.beangle.security.session.protobuf.Model.ProfileOrBuilder>(
              profiles_,
              ((bitField0_ & 0x00000004) != 0),
              getParentForChildren(),
              isClean());
          profiles_ = null;
        }
        return profilesBuilder_;
      }

      private com.google.protobuf.MapField<
          java.lang.String, java.lang.String> details_;

      private com.google.protobuf.MapField<java.lang.String, java.lang.String>
      internalGetDetails() {
        if (details_ == null) {
          return com.google.protobuf.MapField.emptyMapField(
              DetailsDefaultEntryHolder.defaultEntry);
        }
        return details_;
      }

      private com.google.protobuf.MapField<java.lang.String, java.lang.String>
      internalGetMutableDetails() {
        onChanged();
        ;
        if (details_ == null) {
          details_ = com.google.protobuf.MapField.newMapField(
              DetailsDefaultEntryHolder.defaultEntry);
        }
        if (!details_.isMutable()) {
          details_ = details_.copy();
        }
        return details_;
      }

      public int getDetailsCount() {
        return internalGetDetails().getMap().size();
      }

      /**
       * <code>map&lt;string, string&gt; details = 9;</code>
       */

      @java.lang.Override
      public boolean containsDetails(
          java.lang.String key) {
        if (key == null) {
          throw new NullPointerException("map key");
        }
        return internalGetDetails().getMap().containsKey(key);
      }

      /**
       * Use {@link #getDetailsMap()} instead.
       */
      @java.lang.Override
      @java.lang.Deprecated
      public java.util.Map<java.lang.String, java.lang.String> getDetails() {
        return getDetailsMap();
      }

      /**
       * <code>map&lt;string, string&gt; details = 9;</code>
       */
      @java.lang.Override

      public java.util.Map<java.lang.String, java.lang.String> getDetailsMap() {
        return internalGetDetails().getMap();
      }

      /**
       * <code>map&lt;string, string&gt; details = 9;</code>
       */
      @java.lang.Override

      public java.lang.String getDetailsOrDefault(
          java.lang.String key,
          java.lang.String defaultValue) {
        if (key == null) {
          throw new NullPointerException("map key");
        }
        java.util.Map<java.lang.String, java.lang.String> map =
            internalGetDetails().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
      }

      /**
       * <code>map&lt;string, string&gt; details = 9;</code>
       */
      @java.lang.Override

      public java.lang.String getDetailsOrThrow(
          java.lang.String key) {
        if (key == null) {
          throw new NullPointerException("map key");
        }
        java.util.Map<java.lang.String, java.lang.String> map =
            internalGetDetails().getMap();
        if (!map.containsKey(key)) {
          throw new java.lang.IllegalArgumentException();
        }
        return map.get(key);
      }

      public Builder clearDetails() {
        internalGetMutableDetails().getMutableMap()
            .clear();
        return this;
      }

      /**
       * <code>map&lt;string, string&gt; details = 9;</code>
       */

      public Builder removeDetails(
          java.lang.String key) {
        if (key == null) {
          throw new NullPointerException("map key");
        }
        internalGetMutableDetails().getMutableMap()
            .remove(key);
        return this;
      }

      /**
       * Use alternate mutation accessors instead.
       */
      @java.lang.Deprecated
      public java.util.Map<java.lang.String, java.lang.String>
      getMutableDetails() {
        return internalGetMutableDetails().getMutableMap();
      }

      /**
       * <code>map&lt;string, string&gt; details = 9;</code>
       */
      public Builder putDetails(
          java.lang.String key,
          java.lang.String value) {
        if (key == null) {
          throw new NullPointerException("map key");
        }
        if (value == null) {
          throw new NullPointerException("map value");
        }

        internalGetMutableDetails().getMutableMap()
            .put(key, value);
        return this;
      }

      /**
       * <code>map&lt;string, string&gt; details = 9;</code>
       */

      public Builder putAllDetails(
          java.util.Map<java.lang.String, java.lang.String> values) {
        internalGetMutableDetails().getMutableMap()
            .putAll(values);
        return this;
      }

      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }

      // @@protoc_insertion_point(builder_scope:Account)
    }

    // @@protoc_insertion_point(class_scope:Account)
    private static final org.beangle.security.session.protobuf.Model.Account DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new org.beangle.security.session.protobuf.Model.Account();
    }

    public static org.beangle.security.session.protobuf.Model.Account getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Account>
        PARSER = new com.google.protobuf.AbstractParser<Account>() {
      @java.lang.Override
      public Account parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Account(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Account> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Account> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public org.beangle.security.session.protobuf.Model.Account getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface AgentOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Agent)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    java.lang.String getName();

    /**
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    com.google.protobuf.ByteString
    getNameBytes();

    /**
     * <code>string ip = 2;</code>
     *
     * @return The ip.
     */
    java.lang.String getIp();

    /**
     * <code>string ip = 2;</code>
     *
     * @return The bytes for ip.
     */
    com.google.protobuf.ByteString
    getIpBytes();

    /**
     * <code>string os = 3;</code>
     *
     * @return The os.
     */
    java.lang.String getOs();

    /**
     * <code>string os = 3;</code>
     *
     * @return The bytes for os.
     */
    com.google.protobuf.ByteString
    getOsBytes();
  }

  /**
   * Protobuf type {@code Agent}
   */
  public static final class Agent extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Agent)
      AgentOrBuilder {
    private static final long serialVersionUID = 0L;

    // Use Agent.newBuilder() to construct.
    private Agent(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private Agent() {
      name_ = "";
      ip_ = "";
      os_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Agent();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }

    private Agent(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              name_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              ip_ = s;
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              os_ = s;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
      return org.beangle.security.session.protobuf.Model.internal_static_Agent_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
      return org.beangle.security.session.protobuf.Model.internal_static_Agent_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.beangle.security.session.protobuf.Model.Agent.class, org.beangle.security.session.protobuf.Model.Agent.Builder.class);
    }

    public static final int NAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object name_;

    /**
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    @java.lang.Override
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      }
    }

    /**
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
    getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int IP_FIELD_NUMBER = 2;
    private volatile java.lang.Object ip_;

    /**
     * <code>string ip = 2;</code>
     *
     * @return The ip.
     */
    @java.lang.Override
    public java.lang.String getIp() {
      java.lang.Object ref = ip_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        ip_ = s;
        return s;
      }
    }

    /**
     * <code>string ip = 2;</code>
     *
     * @return The bytes for ip.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
    getIpBytes() {
      java.lang.Object ref = ip_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        ip_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int OS_FIELD_NUMBER = 3;
    private volatile java.lang.Object os_;

    /**
     * <code>string os = 3;</code>
     *
     * @return The os.
     */
    @java.lang.Override
    public java.lang.String getOs() {
      java.lang.Object ref = os_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        os_ = s;
        return s;
      }
    }

    /**
     * <code>string os = 3;</code>
     *
     * @return The bytes for os.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
    getOsBytes() {
      java.lang.Object ref = os_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        os_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;

    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(ip_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, ip_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(os_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, os_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(ip_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, ip_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(os_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, os_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof org.beangle.security.session.protobuf.Model.Agent)) {
        return super.equals(obj);
      }
      org.beangle.security.session.protobuf.Model.Agent other = (org.beangle.security.session.protobuf.Model.Agent) obj;

      if (!getName()
          .equals(other.getName())) return false;
      if (!getIp()
          .equals(other.getIp())) return false;
      if (!getOs()
          .equals(other.getOs())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
      hash = (37 * hash) + IP_FIELD_NUMBER;
      hash = (53 * hash) + getIp().hashCode();
      hash = (37 * hash) + OS_FIELD_NUMBER;
      hash = (53 * hash) + getOs().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Agent parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(org.beangle.security.session.protobuf.Model.Agent prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }

    /**
     * Protobuf type {@code Agent}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Agent)
        org.beangle.security.session.protobuf.Model.AgentOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
        return org.beangle.security.session.protobuf.Model.internal_static_Agent_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
        return org.beangle.security.session.protobuf.Model.internal_static_Agent_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                org.beangle.security.session.protobuf.Model.Agent.class, org.beangle.security.session.protobuf.Model.Agent.Builder.class);
      }

      // Construct using org.beangle.security.session.protobuf.Model.Agent.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
            .alwaysUseFieldBuilders) {
        }
      }

      @java.lang.Override
      public Builder clear() {
        super.clear();
        name_ = "";

        ip_ = "";

        os_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
      getDescriptorForType() {
        return org.beangle.security.session.protobuf.Model.internal_static_Agent_descriptor;
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Agent getDefaultInstanceForType() {
        return org.beangle.security.session.protobuf.Model.Agent.getDefaultInstance();
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Agent build() {
        org.beangle.security.session.protobuf.Model.Agent result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Agent buildPartial() {
        org.beangle.security.session.protobuf.Model.Agent result = new org.beangle.security.session.protobuf.Model.Agent(this);
        result.name_ = name_;
        result.ip_ = ip_;
        result.os_ = os_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }

      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }

      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }

      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }

      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }

      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }

      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.beangle.security.session.protobuf.Model.Agent) {
          return mergeFrom((org.beangle.security.session.protobuf.Model.Agent) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(org.beangle.security.session.protobuf.Model.Agent other) {
        if (other == org.beangle.security.session.protobuf.Model.Agent.getDefaultInstance()) return this;
        if (!other.getName().isEmpty()) {
          name_ = other.name_;
          onChanged();
        }
        if (!other.getIp().isEmpty()) {
          ip_ = other.ip_;
          onChanged();
        }
        if (!other.getOs().isEmpty()) {
          os_ = other.os_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        org.beangle.security.session.protobuf.Model.Agent parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (org.beangle.security.session.protobuf.Model.Agent) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object name_ = "";

      /**
       * <code>string name = 1;</code>
       *
       * @return The name.
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          name_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }

      /**
       * <code>string name = 1;</code>
       *
       * @return The bytes for name.
       */
      public com.google.protobuf.ByteString
      getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      /**
       * <code>string name = 1;</code>
       *
       * @param value The name to set.
       * @return This builder for chaining.
       */
      public Builder setName(
          java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        name_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>string name = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearName() {

        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }

      /**
       * <code>string name = 1;</code>
       *
       * @param value The bytes for name to set.
       * @return This builder for chaining.
       */
      public Builder setNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        name_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object ip_ = "";

      /**
       * <code>string ip = 2;</code>
       *
       * @return The ip.
       */
      public java.lang.String getIp() {
        java.lang.Object ref = ip_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          ip_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }

      /**
       * <code>string ip = 2;</code>
       *
       * @return The bytes for ip.
       */
      public com.google.protobuf.ByteString
      getIpBytes() {
        java.lang.Object ref = ip_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          ip_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      /**
       * <code>string ip = 2;</code>
       *
       * @param value The ip to set.
       * @return This builder for chaining.
       */
      public Builder setIp(
          java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        ip_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>string ip = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearIp() {

        ip_ = getDefaultInstance().getIp();
        onChanged();
        return this;
      }

      /**
       * <code>string ip = 2;</code>
       *
       * @param value The bytes for ip to set.
       * @return This builder for chaining.
       */
      public Builder setIpBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        ip_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object os_ = "";

      /**
       * <code>string os = 3;</code>
       *
       * @return The os.
       */
      public java.lang.String getOs() {
        java.lang.Object ref = os_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          os_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }

      /**
       * <code>string os = 3;</code>
       *
       * @return The bytes for os.
       */
      public com.google.protobuf.ByteString
      getOsBytes() {
        java.lang.Object ref = os_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          os_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      /**
       * <code>string os = 3;</code>
       *
       * @param value The os to set.
       * @return This builder for chaining.
       */
      public Builder setOs(
          java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        os_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>string os = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearOs() {

        os_ = getDefaultInstance().getOs();
        onChanged();
        return this;
      }

      /**
       * <code>string os = 3;</code>
       *
       * @param value The bytes for os to set.
       * @return This builder for chaining.
       */
      public Builder setOsBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        os_ = value;
        onChanged();
        return this;
      }

      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }

      // @@protoc_insertion_point(builder_scope:Agent)
    }

    // @@protoc_insertion_point(class_scope:Agent)
    private static final org.beangle.security.session.protobuf.Model.Agent DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new org.beangle.security.session.protobuf.Model.Agent();
    }

    public static org.beangle.security.session.protobuf.Model.Agent getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Agent>
        PARSER = new com.google.protobuf.AbstractParser<Agent>() {
      @java.lang.Override
      public Agent parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Agent(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Agent> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Agent> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public org.beangle.security.session.protobuf.Model.Agent getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface SessionOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Session)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string id = 1;</code>
     *
     * @return The id.
     */
    java.lang.String getId();

    /**
     * <code>string id = 1;</code>
     *
     * @return The bytes for id.
     */
    com.google.protobuf.ByteString
    getIdBytes();

    /**
     * <code>.Account principal = 2;</code>
     *
     * @return Whether the principal field is set.
     */
    boolean hasPrincipal();

    /**
     * <code>.Account principal = 2;</code>
     *
     * @return The principal.
     */
    org.beangle.security.session.protobuf.Model.Account getPrincipal();

    /**
     * <code>.Account principal = 2;</code>
     */
    org.beangle.security.session.protobuf.Model.AccountOrBuilder getPrincipalOrBuilder();

    /**
     * <code>int64 loginAt = 3;</code>
     *
     * @return The loginAt.
     */
    long getLoginAt();

    /**
     * <code>int64 lastAccessAt = 4;</code>
     *
     * @return The lastAccessAt.
     */
    long getLastAccessAt();

    /**
     * <code>.Agent agent = 5;</code>
     *
     * @return Whether the agent field is set.
     */
    boolean hasAgent();

    /**
     * <code>.Agent agent = 5;</code>
     *
     * @return The agent.
     */
    org.beangle.security.session.protobuf.Model.Agent getAgent();

    /**
     * <code>.Agent agent = 5;</code>
     */
    org.beangle.security.session.protobuf.Model.AgentOrBuilder getAgentOrBuilder();

    /**
     * <code>int32 ttiSeconds = 6;</code>
     *
     * @return The ttiSeconds.
     */
    int getTtiSeconds();
  }

  /**
   * Protobuf type {@code Session}
   */
  public static final class Session extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Session)
      SessionOrBuilder {
    private static final long serialVersionUID = 0L;

    // Use Session.newBuilder() to construct.
    private Session(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private Session() {
      id_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Session();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }

    private Session(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              id_ = s;
              break;
            }
            case 18: {
              org.beangle.security.session.protobuf.Model.Account.Builder subBuilder = null;
              if (principal_ != null) {
                subBuilder = principal_.toBuilder();
              }
              principal_ = input.readMessage(org.beangle.security.session.protobuf.Model.Account.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(principal_);
                principal_ = subBuilder.buildPartial();
              }

              break;
            }
            case 24: {

              loginAt_ = input.readInt64();
              break;
            }
            case 32: {

              lastAccessAt_ = input.readInt64();
              break;
            }
            case 42: {
              org.beangle.security.session.protobuf.Model.Agent.Builder subBuilder = null;
              if (agent_ != null) {
                subBuilder = agent_.toBuilder();
              }
              agent_ = input.readMessage(org.beangle.security.session.protobuf.Model.Agent.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(agent_);
                agent_ = subBuilder.buildPartial();
              }

              break;
            }
            case 48: {

              ttiSeconds_ = input.readInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
      return org.beangle.security.session.protobuf.Model.internal_static_Session_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
      return org.beangle.security.session.protobuf.Model.internal_static_Session_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.beangle.security.session.protobuf.Model.Session.class, org.beangle.security.session.protobuf.Model.Session.Builder.class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    private volatile java.lang.Object id_;

    /**
     * <code>string id = 1;</code>
     *
     * @return The id.
     */
    @java.lang.Override
    public java.lang.String getId() {
      java.lang.Object ref = id_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        id_ = s;
        return s;
      }
    }

    /**
     * <code>string id = 1;</code>
     *
     * @return The bytes for id.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
    getIdBytes() {
      java.lang.Object ref = id_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        id_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PRINCIPAL_FIELD_NUMBER = 2;
    private org.beangle.security.session.protobuf.Model.Account principal_;

    /**
     * <code>.Account principal = 2;</code>
     *
     * @return Whether the principal field is set.
     */
    @java.lang.Override
    public boolean hasPrincipal() {
      return principal_ != null;
    }

    /**
     * <code>.Account principal = 2;</code>
     *
     * @return The principal.
     */
    @java.lang.Override
    public org.beangle.security.session.protobuf.Model.Account getPrincipal() {
      return principal_ == null ? org.beangle.security.session.protobuf.Model.Account.getDefaultInstance() : principal_;
    }

    /**
     * <code>.Account principal = 2;</code>
     */
    @java.lang.Override
    public org.beangle.security.session.protobuf.Model.AccountOrBuilder getPrincipalOrBuilder() {
      return getPrincipal();
    }

    public static final int LOGINAT_FIELD_NUMBER = 3;
    private long loginAt_;

    /**
     * <code>int64 loginAt = 3;</code>
     *
     * @return The loginAt.
     */
    @java.lang.Override
    public long getLoginAt() {
      return loginAt_;
    }

    public static final int LASTACCESSAT_FIELD_NUMBER = 4;
    private long lastAccessAt_;

    /**
     * <code>int64 lastAccessAt = 4;</code>
     *
     * @return The lastAccessAt.
     */
    @java.lang.Override
    public long getLastAccessAt() {
      return lastAccessAt_;
    }

    public static final int AGENT_FIELD_NUMBER = 5;
    private org.beangle.security.session.protobuf.Model.Agent agent_;

    /**
     * <code>.Agent agent = 5;</code>
     *
     * @return Whether the agent field is set.
     */
    @java.lang.Override
    public boolean hasAgent() {
      return agent_ != null;
    }

    /**
     * <code>.Agent agent = 5;</code>
     *
     * @return The agent.
     */
    @java.lang.Override
    public org.beangle.security.session.protobuf.Model.Agent getAgent() {
      return agent_ == null ? org.beangle.security.session.protobuf.Model.Agent.getDefaultInstance() : agent_;
    }

    /**
     * <code>.Agent agent = 5;</code>
     */
    @java.lang.Override
    public org.beangle.security.session.protobuf.Model.AgentOrBuilder getAgentOrBuilder() {
      return getAgent();
    }

    public static final int TTISECONDS_FIELD_NUMBER = 6;
    private int ttiSeconds_;

    /**
     * <code>int32 ttiSeconds = 6;</code>
     *
     * @return The ttiSeconds.
     */
    @java.lang.Override
    public int getTtiSeconds() {
      return ttiSeconds_;
    }

    private byte memoizedIsInitialized = -1;

    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(id_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, id_);
      }
      if (principal_ != null) {
        output.writeMessage(2, getPrincipal());
      }
      if (loginAt_ != 0L) {
        output.writeInt64(3, loginAt_);
      }
      if (lastAccessAt_ != 0L) {
        output.writeInt64(4, lastAccessAt_);
      }
      if (agent_ != null) {
        output.writeMessage(5, getAgent());
      }
      if (ttiSeconds_ != 0) {
        output.writeInt32(6, ttiSeconds_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(id_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, id_);
      }
      if (principal_ != null) {
        size += com.google.protobuf.CodedOutputStream
            .computeMessageSize(2, getPrincipal());
      }
      if (loginAt_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
            .computeInt64Size(3, loginAt_);
      }
      if (lastAccessAt_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
            .computeInt64Size(4, lastAccessAt_);
      }
      if (agent_ != null) {
        size += com.google.protobuf.CodedOutputStream
            .computeMessageSize(5, getAgent());
      }
      if (ttiSeconds_ != 0) {
        size += com.google.protobuf.CodedOutputStream
            .computeInt32Size(6, ttiSeconds_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof org.beangle.security.session.protobuf.Model.Session)) {
        return super.equals(obj);
      }
      org.beangle.security.session.protobuf.Model.Session other = (org.beangle.security.session.protobuf.Model.Session) obj;

      if (!getId()
          .equals(other.getId())) return false;
      if (hasPrincipal() != other.hasPrincipal()) return false;
      if (hasPrincipal()) {
        if (!getPrincipal()
            .equals(other.getPrincipal())) return false;
      }
      if (getLoginAt()
          != other.getLoginAt()) return false;
      if (getLastAccessAt()
          != other.getLastAccessAt()) return false;
      if (hasAgent() != other.hasAgent()) return false;
      if (hasAgent()) {
        if (!getAgent()
            .equals(other.getAgent())) return false;
      }
      if (getTtiSeconds()
          != other.getTtiSeconds()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + getId().hashCode();
      if (hasPrincipal()) {
        hash = (37 * hash) + PRINCIPAL_FIELD_NUMBER;
        hash = (53 * hash) + getPrincipal().hashCode();
      }
      hash = (37 * hash) + LOGINAT_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getLoginAt());
      hash = (37 * hash) + LASTACCESSAT_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getLastAccessAt());
      if (hasAgent()) {
        hash = (37 * hash) + AGENT_FIELD_NUMBER;
        hash = (53 * hash) + getAgent().hashCode();
      }
      hash = (37 * hash) + TTISECONDS_FIELD_NUMBER;
      hash = (53 * hash) + getTtiSeconds();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static org.beangle.security.session.protobuf.Model.Session parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Session parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Session parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Session parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Session parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Session parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Session parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Session parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Session parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Session parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Session parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Session parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(org.beangle.security.session.protobuf.Model.Session prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }

    /**
     * Protobuf type {@code Session}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Session)
        org.beangle.security.session.protobuf.Model.SessionOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
        return org.beangle.security.session.protobuf.Model.internal_static_Session_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
        return org.beangle.security.session.protobuf.Model.internal_static_Session_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                org.beangle.security.session.protobuf.Model.Session.class, org.beangle.security.session.protobuf.Model.Session.Builder.class);
      }

      // Construct using org.beangle.security.session.protobuf.Model.Session.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
            .alwaysUseFieldBuilders) {
        }
      }

      @java.lang.Override
      public Builder clear() {
        super.clear();
        id_ = "";

        if (principalBuilder_ == null) {
          principal_ = null;
        } else {
          principal_ = null;
          principalBuilder_ = null;
        }
        loginAt_ = 0L;

        lastAccessAt_ = 0L;

        if (agentBuilder_ == null) {
          agent_ = null;
        } else {
          agent_ = null;
          agentBuilder_ = null;
        }
        ttiSeconds_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
      getDescriptorForType() {
        return org.beangle.security.session.protobuf.Model.internal_static_Session_descriptor;
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Session getDefaultInstanceForType() {
        return org.beangle.security.session.protobuf.Model.Session.getDefaultInstance();
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Session build() {
        org.beangle.security.session.protobuf.Model.Session result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Session buildPartial() {
        org.beangle.security.session.protobuf.Model.Session result = new org.beangle.security.session.protobuf.Model.Session(this);
        result.id_ = id_;
        if (principalBuilder_ == null) {
          result.principal_ = principal_;
        } else {
          result.principal_ = principalBuilder_.build();
        }
        result.loginAt_ = loginAt_;
        result.lastAccessAt_ = lastAccessAt_;
        if (agentBuilder_ == null) {
          result.agent_ = agent_;
        } else {
          result.agent_ = agentBuilder_.build();
        }
        result.ttiSeconds_ = ttiSeconds_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }

      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }

      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }

      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }

      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }

      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }

      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.beangle.security.session.protobuf.Model.Session) {
          return mergeFrom((org.beangle.security.session.protobuf.Model.Session) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(org.beangle.security.session.protobuf.Model.Session other) {
        if (other == org.beangle.security.session.protobuf.Model.Session.getDefaultInstance()) return this;
        if (!other.getId().isEmpty()) {
          id_ = other.id_;
          onChanged();
        }
        if (other.hasPrincipal()) {
          mergePrincipal(other.getPrincipal());
        }
        if (other.getLoginAt() != 0L) {
          setLoginAt(other.getLoginAt());
        }
        if (other.getLastAccessAt() != 0L) {
          setLastAccessAt(other.getLastAccessAt());
        }
        if (other.hasAgent()) {
          mergeAgent(other.getAgent());
        }
        if (other.getTtiSeconds() != 0) {
          setTtiSeconds(other.getTtiSeconds());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        org.beangle.security.session.protobuf.Model.Session parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (org.beangle.security.session.protobuf.Model.Session) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object id_ = "";

      /**
       * <code>string id = 1;</code>
       *
       * @return The id.
       */
      public java.lang.String getId() {
        java.lang.Object ref = id_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          id_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }

      /**
       * <code>string id = 1;</code>
       *
       * @return The bytes for id.
       */
      public com.google.protobuf.ByteString
      getIdBytes() {
        java.lang.Object ref = id_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          id_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      /**
       * <code>string id = 1;</code>
       *
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(
          java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        id_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>string id = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearId() {

        id_ = getDefaultInstance().getId();
        onChanged();
        return this;
      }

      /**
       * <code>string id = 1;</code>
       *
       * @param value The bytes for id to set.
       * @return This builder for chaining.
       */
      public Builder setIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        id_ = value;
        onChanged();
        return this;
      }

      private org.beangle.security.session.protobuf.Model.Account principal_;
      private com.google.protobuf.SingleFieldBuilderV3<
          org.beangle.security.session.protobuf.Model.Account, org.beangle.security.session.protobuf.Model.Account.Builder, org.beangle.security.session.protobuf.Model.AccountOrBuilder> principalBuilder_;

      /**
       * <code>.Account principal = 2;</code>
       *
       * @return Whether the principal field is set.
       */
      public boolean hasPrincipal() {
        return principalBuilder_ != null || principal_ != null;
      }

      /**
       * <code>.Account principal = 2;</code>
       *
       * @return The principal.
       */
      public org.beangle.security.session.protobuf.Model.Account getPrincipal() {
        if (principalBuilder_ == null) {
          return principal_ == null ? org.beangle.security.session.protobuf.Model.Account.getDefaultInstance() : principal_;
        } else {
          return principalBuilder_.getMessage();
        }
      }

      /**
       * <code>.Account principal = 2;</code>
       */
      public Builder setPrincipal(org.beangle.security.session.protobuf.Model.Account value) {
        if (principalBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          principal_ = value;
          onChanged();
        } else {
          principalBuilder_.setMessage(value);
        }

        return this;
      }

      /**
       * <code>.Account principal = 2;</code>
       */
      public Builder setPrincipal(
          org.beangle.security.session.protobuf.Model.Account.Builder builderForValue) {
        if (principalBuilder_ == null) {
          principal_ = builderForValue.build();
          onChanged();
        } else {
          principalBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }

      /**
       * <code>.Account principal = 2;</code>
       */
      public Builder mergePrincipal(org.beangle.security.session.protobuf.Model.Account value) {
        if (principalBuilder_ == null) {
          if (principal_ != null) {
            principal_ =
                org.beangle.security.session.protobuf.Model.Account.newBuilder(principal_).mergeFrom(value).buildPartial();
          } else {
            principal_ = value;
          }
          onChanged();
        } else {
          principalBuilder_.mergeFrom(value);
        }

        return this;
      }

      /**
       * <code>.Account principal = 2;</code>
       */
      public Builder clearPrincipal() {
        if (principalBuilder_ == null) {
          principal_ = null;
          onChanged();
        } else {
          principal_ = null;
          principalBuilder_ = null;
        }

        return this;
      }

      /**
       * <code>.Account principal = 2;</code>
       */
      public org.beangle.security.session.protobuf.Model.Account.Builder getPrincipalBuilder() {

        onChanged();
        return getPrincipalFieldBuilder().getBuilder();
      }

      /**
       * <code>.Account principal = 2;</code>
       */
      public org.beangle.security.session.protobuf.Model.AccountOrBuilder getPrincipalOrBuilder() {
        if (principalBuilder_ != null) {
          return principalBuilder_.getMessageOrBuilder();
        } else {
          return principal_ == null ?
              org.beangle.security.session.protobuf.Model.Account.getDefaultInstance() : principal_;
        }
      }

      /**
       * <code>.Account principal = 2;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          org.beangle.security.session.protobuf.Model.Account, org.beangle.security.session.protobuf.Model.Account.Builder, org.beangle.security.session.protobuf.Model.AccountOrBuilder>
      getPrincipalFieldBuilder() {
        if (principalBuilder_ == null) {
          principalBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              org.beangle.security.session.protobuf.Model.Account, org.beangle.security.session.protobuf.Model.Account.Builder, org.beangle.security.session.protobuf.Model.AccountOrBuilder>(
              getPrincipal(),
              getParentForChildren(),
              isClean());
          principal_ = null;
        }
        return principalBuilder_;
      }

      private long loginAt_;

      /**
       * <code>int64 loginAt = 3;</code>
       *
       * @return The loginAt.
       */
      @java.lang.Override
      public long getLoginAt() {
        return loginAt_;
      }

      /**
       * <code>int64 loginAt = 3;</code>
       *
       * @param value The loginAt to set.
       * @return This builder for chaining.
       */
      public Builder setLoginAt(long value) {

        loginAt_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>int64 loginAt = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoginAt() {

        loginAt_ = 0L;
        onChanged();
        return this;
      }

      private long lastAccessAt_;

      /**
       * <code>int64 lastAccessAt = 4;</code>
       *
       * @return The lastAccessAt.
       */
      @java.lang.Override
      public long getLastAccessAt() {
        return lastAccessAt_;
      }

      /**
       * <code>int64 lastAccessAt = 4;</code>
       *
       * @param value The lastAccessAt to set.
       * @return This builder for chaining.
       */
      public Builder setLastAccessAt(long value) {

        lastAccessAt_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>int64 lastAccessAt = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLastAccessAt() {

        lastAccessAt_ = 0L;
        onChanged();
        return this;
      }

      private org.beangle.security.session.protobuf.Model.Agent agent_;
      private com.google.protobuf.SingleFieldBuilderV3<
          org.beangle.security.session.protobuf.Model.Agent, org.beangle.security.session.protobuf.Model.Agent.Builder, org.beangle.security.session.protobuf.Model.AgentOrBuilder> agentBuilder_;

      /**
       * <code>.Agent agent = 5;</code>
       *
       * @return Whether the agent field is set.
       */
      public boolean hasAgent() {
        return agentBuilder_ != null || agent_ != null;
      }

      /**
       * <code>.Agent agent = 5;</code>
       *
       * @return The agent.
       */
      public org.beangle.security.session.protobuf.Model.Agent getAgent() {
        if (agentBuilder_ == null) {
          return agent_ == null ? org.beangle.security.session.protobuf.Model.Agent.getDefaultInstance() : agent_;
        } else {
          return agentBuilder_.getMessage();
        }
      }

      /**
       * <code>.Agent agent = 5;</code>
       */
      public Builder setAgent(org.beangle.security.session.protobuf.Model.Agent value) {
        if (agentBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          agent_ = value;
          onChanged();
        } else {
          agentBuilder_.setMessage(value);
        }

        return this;
      }

      /**
       * <code>.Agent agent = 5;</code>
       */
      public Builder setAgent(
          org.beangle.security.session.protobuf.Model.Agent.Builder builderForValue) {
        if (agentBuilder_ == null) {
          agent_ = builderForValue.build();
          onChanged();
        } else {
          agentBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }

      /**
       * <code>.Agent agent = 5;</code>
       */
      public Builder mergeAgent(org.beangle.security.session.protobuf.Model.Agent value) {
        if (agentBuilder_ == null) {
          if (agent_ != null) {
            agent_ =
                org.beangle.security.session.protobuf.Model.Agent.newBuilder(agent_).mergeFrom(value).buildPartial();
          } else {
            agent_ = value;
          }
          onChanged();
        } else {
          agentBuilder_.mergeFrom(value);
        }

        return this;
      }

      /**
       * <code>.Agent agent = 5;</code>
       */
      public Builder clearAgent() {
        if (agentBuilder_ == null) {
          agent_ = null;
          onChanged();
        } else {
          agent_ = null;
          agentBuilder_ = null;
        }

        return this;
      }

      /**
       * <code>.Agent agent = 5;</code>
       */
      public org.beangle.security.session.protobuf.Model.Agent.Builder getAgentBuilder() {

        onChanged();
        return getAgentFieldBuilder().getBuilder();
      }

      /**
       * <code>.Agent agent = 5;</code>
       */
      public org.beangle.security.session.protobuf.Model.AgentOrBuilder getAgentOrBuilder() {
        if (agentBuilder_ != null) {
          return agentBuilder_.getMessageOrBuilder();
        } else {
          return agent_ == null ?
              org.beangle.security.session.protobuf.Model.Agent.getDefaultInstance() : agent_;
        }
      }

      /**
       * <code>.Agent agent = 5;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          org.beangle.security.session.protobuf.Model.Agent, org.beangle.security.session.protobuf.Model.Agent.Builder, org.beangle.security.session.protobuf.Model.AgentOrBuilder>
      getAgentFieldBuilder() {
        if (agentBuilder_ == null) {
          agentBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              org.beangle.security.session.protobuf.Model.Agent, org.beangle.security.session.protobuf.Model.Agent.Builder, org.beangle.security.session.protobuf.Model.AgentOrBuilder>(
              getAgent(),
              getParentForChildren(),
              isClean());
          agent_ = null;
        }
        return agentBuilder_;
      }

      private int ttiSeconds_;

      /**
       * <code>int32 ttiSeconds = 6;</code>
       *
       * @return The ttiSeconds.
       */
      @java.lang.Override
      public int getTtiSeconds() {
        return ttiSeconds_;
      }

      /**
       * <code>int32 ttiSeconds = 6;</code>
       *
       * @param value The ttiSeconds to set.
       * @return This builder for chaining.
       */
      public Builder setTtiSeconds(int value) {

        ttiSeconds_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>int32 ttiSeconds = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTtiSeconds() {

        ttiSeconds_ = 0;
        onChanged();
        return this;
      }

      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }

      // @@protoc_insertion_point(builder_scope:Session)
    }

    // @@protoc_insertion_point(class_scope:Session)
    private static final org.beangle.security.session.protobuf.Model.Session DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new org.beangle.security.session.protobuf.Model.Session();
    }

    public static org.beangle.security.session.protobuf.Model.Session getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Session>
        PARSER = new com.google.protobuf.AbstractParser<Session>() {
      @java.lang.Override
      public Session parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Session(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Session> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Session> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public org.beangle.security.session.protobuf.Model.Session getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface ProfileOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Profile)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>string name = 2;</code>
     *
     * @return The name.
     */
    java.lang.String getName();

    /**
     * <code>string name = 2;</code>
     *
     * @return The bytes for name.
     */
    com.google.protobuf.ByteString
    getNameBytes();

    /**
     * <code>map&lt;string, string&gt; properties = 3;</code>
     */
    int getPropertiesCount();

    /**
     * <code>map&lt;string, string&gt; properties = 3;</code>
     */
    boolean containsProperties(
        java.lang.String key);

    /**
     * Use {@link #getPropertiesMap()} instead.
     */
    @java.lang.Deprecated
    java.util.Map<java.lang.String, java.lang.String>
    getProperties();

    /**
     * <code>map&lt;string, string&gt; properties = 3;</code>
     */
    java.util.Map<java.lang.String, java.lang.String>
    getPropertiesMap();

    /**
     * <code>map&lt;string, string&gt; properties = 3;</code>
     */

    /* nullable */
    java.lang.String getPropertiesOrDefault(
        java.lang.String key,
        /* nullable */
        java.lang.String defaultValue);

    /**
     * <code>map&lt;string, string&gt; properties = 3;</code>
     */

    java.lang.String getPropertiesOrThrow(
        java.lang.String key);
  }

  /**
   * Protobuf type {@code Profile}
   */
  public static final class Profile extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Profile)
      ProfileOrBuilder {
    private static final long serialVersionUID = 0L;

    // Use Profile.newBuilder() to construct.
    private Profile(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private Profile() {
      name_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Profile();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }

    private Profile(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              id_ = input.readInt64();
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              name_ = s;
              break;
            }
            case 26: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                properties_ = com.google.protobuf.MapField.newMapField(
                    PropertiesDefaultEntryHolder.defaultEntry);
                mutable_bitField0_ |= 0x00000001;
              }
              com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
                  properties__ = input.readMessage(
                  PropertiesDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
              properties_.getMutableMap().put(
                  properties__.getKey(), properties__.getValue());
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
      return org.beangle.security.session.protobuf.Model.internal_static_Profile_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    @java.lang.Override
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 3:
          return internalGetProperties();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
      return org.beangle.security.session.protobuf.Model.internal_static_Profile_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.beangle.security.session.protobuf.Model.Profile.class, org.beangle.security.session.protobuf.Model.Profile.Builder.class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    private long id_;

    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    @java.lang.Override
    public long getId() {
      return id_;
    }

    public static final int NAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object name_;

    /**
     * <code>string name = 2;</code>
     *
     * @return The name.
     */
    @java.lang.Override
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      }
    }

    /**
     * <code>string name = 2;</code>
     *
     * @return The bytes for name.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
    getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PROPERTIES_FIELD_NUMBER = 3;

    private static final class PropertiesDefaultEntryHolder {
      static final com.google.protobuf.MapEntry<
          java.lang.String, java.lang.String> defaultEntry =
          com.google.protobuf.MapEntry
              .<java.lang.String, java.lang.String>newDefaultInstance(
                  org.beangle.security.session.protobuf.Model.internal_static_Profile_PropertiesEntry_descriptor,
                  com.google.protobuf.WireFormat.FieldType.STRING,
                  "",
                  com.google.protobuf.WireFormat.FieldType.STRING,
                  "");
    }

    private com.google.protobuf.MapField<
        java.lang.String, java.lang.String> properties_;

    private com.google.protobuf.MapField<java.lang.String, java.lang.String>
    internalGetProperties() {
      if (properties_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            PropertiesDefaultEntryHolder.defaultEntry);
      }
      return properties_;
    }

    public int getPropertiesCount() {
      return internalGetProperties().getMap().size();
    }

    /**
     * <code>map&lt;string, string&gt; properties = 3;</code>
     */

    @java.lang.Override
    public boolean containsProperties(
        java.lang.String key) {
      if (key == null) {
        throw new NullPointerException("map key");
      }
      return internalGetProperties().getMap().containsKey(key);
    }

    /**
     * Use {@link #getPropertiesMap()} instead.
     */
    @java.lang.Override
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String> getProperties() {
      return getPropertiesMap();
    }

    /**
     * <code>map&lt;string, string&gt; properties = 3;</code>
     */
    @java.lang.Override

    public java.util.Map<java.lang.String, java.lang.String> getPropertiesMap() {
      return internalGetProperties().getMap();
    }

    /**
     * <code>map&lt;string, string&gt; properties = 3;</code>
     */
    @java.lang.Override

    public java.lang.String getPropertiesOrDefault(
        java.lang.String key,
        java.lang.String defaultValue) {
      if (key == null) {
        throw new NullPointerException("map key");
      }
      java.util.Map<java.lang.String, java.lang.String> map =
          internalGetProperties().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    /**
     * <code>map&lt;string, string&gt; properties = 3;</code>
     */
    @java.lang.Override

    public java.lang.String getPropertiesOrThrow(
        java.lang.String key) {
      if (key == null) {
        throw new NullPointerException("map key");
      }
      java.util.Map<java.lang.String, java.lang.String> map =
          internalGetProperties().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    private byte memoizedIsInitialized = -1;

    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
        throws java.io.IOException {
      if (id_ != 0L) {
        output.writeInt64(1, id_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, name_);
      }
      com.google.protobuf.GeneratedMessageV3
          .serializeStringMapTo(
              output,
              internalGetProperties(),
              PropertiesDefaultEntryHolder.defaultEntry,
              3);
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (id_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
            .computeInt64Size(1, id_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, name_);
      }
      for (java.util.Map.Entry<java.lang.String, java.lang.String> entry
          : internalGetProperties().getMap().entrySet()) {
        com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
            properties__ = PropertiesDefaultEntryHolder.defaultEntry.newBuilderForType()
            .setKey(entry.getKey())
            .setValue(entry.getValue())
            .build();
        size += com.google.protobuf.CodedOutputStream
            .computeMessageSize(3, properties__);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof org.beangle.security.session.protobuf.Model.Profile)) {
        return super.equals(obj);
      }
      org.beangle.security.session.protobuf.Model.Profile other = (org.beangle.security.session.protobuf.Model.Profile) obj;

      if (getId()
          != other.getId()) return false;
      if (!getName()
          .equals(other.getName())) return false;
      if (!internalGetProperties().equals(
          other.internalGetProperties())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getId());
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
      if (!internalGetProperties().getMap().isEmpty()) {
        hash = (37 * hash) + PROPERTIES_FIELD_NUMBER;
        hash = (53 * hash) + internalGetProperties().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }

    public static org.beangle.security.session.protobuf.Model.Profile parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(org.beangle.security.session.protobuf.Model.Profile prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }

    /**
     * Protobuf type {@code Profile}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Profile)
        org.beangle.security.session.protobuf.Model.ProfileOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
        return org.beangle.security.session.protobuf.Model.internal_static_Profile_descriptor;
      }

      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMapField(
          int number) {
        switch (number) {
          case 3:
            return internalGetProperties();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }

      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMutableMapField(
          int number) {
        switch (number) {
          case 3:
            return internalGetMutableProperties();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
        return org.beangle.security.session.protobuf.Model.internal_static_Profile_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                org.beangle.security.session.protobuf.Model.Profile.class, org.beangle.security.session.protobuf.Model.Profile.Builder.class);
      }

      // Construct using org.beangle.security.session.protobuf.Model.Profile.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
            .alwaysUseFieldBuilders) {
        }
      }

      @java.lang.Override
      public Builder clear() {
        super.clear();
        id_ = 0L;

        name_ = "";

        internalGetMutableProperties().clear();
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
      getDescriptorForType() {
        return org.beangle.security.session.protobuf.Model.internal_static_Profile_descriptor;
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Profile getDefaultInstanceForType() {
        return org.beangle.security.session.protobuf.Model.Profile.getDefaultInstance();
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Profile build() {
        org.beangle.security.session.protobuf.Model.Profile result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public org.beangle.security.session.protobuf.Model.Profile buildPartial() {
        org.beangle.security.session.protobuf.Model.Profile result = new org.beangle.security.session.protobuf.Model.Profile(this);
        int from_bitField0_ = bitField0_;
        result.id_ = id_;
        result.name_ = name_;
        result.properties_ = internalGetProperties();
        result.properties_.makeImmutable();
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }

      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }

      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }

      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }

      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }

      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }

      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.beangle.security.session.protobuf.Model.Profile) {
          return mergeFrom((org.beangle.security.session.protobuf.Model.Profile) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(org.beangle.security.session.protobuf.Model.Profile other) {
        if (other == org.beangle.security.session.protobuf.Model.Profile.getDefaultInstance()) return this;
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (!other.getName().isEmpty()) {
          name_ = other.name_;
          onChanged();
        }
        internalGetMutableProperties().mergeFrom(
            other.internalGetProperties());
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        org.beangle.security.session.protobuf.Model.Profile parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (org.beangle.security.session.protobuf.Model.Profile) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int bitField0_;

      private long id_;

      /**
       * <code>int64 id = 1;</code>
       *
       * @return The id.
       */
      @java.lang.Override
      public long getId() {
        return id_;
      }

      /**
       * <code>int64 id = 1;</code>
       *
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(long value) {

        id_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>int64 id = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearId() {

        id_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object name_ = "";

      /**
       * <code>string name = 2;</code>
       *
       * @return The name.
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          name_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }

      /**
       * <code>string name = 2;</code>
       *
       * @return The bytes for name.
       */
      public com.google.protobuf.ByteString
      getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      /**
       * <code>string name = 2;</code>
       *
       * @param value The name to set.
       * @return This builder for chaining.
       */
      public Builder setName(
          java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        name_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>string name = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearName() {

        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }

      /**
       * <code>string name = 2;</code>
       *
       * @param value The bytes for name to set.
       * @return This builder for chaining.
       */
      public Builder setNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        name_ = value;
        onChanged();
        return this;
      }

      private com.google.protobuf.MapField<
          java.lang.String, java.lang.String> properties_;

      private com.google.protobuf.MapField<java.lang.String, java.lang.String>
      internalGetProperties() {
        if (properties_ == null) {
          return com.google.protobuf.MapField.emptyMapField(
              PropertiesDefaultEntryHolder.defaultEntry);
        }
        return properties_;
      }

      private com.google.protobuf.MapField<java.lang.String, java.lang.String>
      internalGetMutableProperties() {
        onChanged();
        ;
        if (properties_ == null) {
          properties_ = com.google.protobuf.MapField.newMapField(
              PropertiesDefaultEntryHolder.defaultEntry);
        }
        if (!properties_.isMutable()) {
          properties_ = properties_.copy();
        }
        return properties_;
      }

      public int getPropertiesCount() {
        return internalGetProperties().getMap().size();
      }

      /**
       * <code>map&lt;string, string&gt; properties = 3;</code>
       */

      @java.lang.Override
      public boolean containsProperties(
          java.lang.String key) {
        if (key == null) {
          throw new NullPointerException("map key");
        }
        return internalGetProperties().getMap().containsKey(key);
      }

      /**
       * Use {@link #getPropertiesMap()} instead.
       */
      @java.lang.Override
      @java.lang.Deprecated
      public java.util.Map<java.lang.String, java.lang.String> getProperties() {
        return getPropertiesMap();
      }

      /**
       * <code>map&lt;string, string&gt; properties = 3;</code>
       */
      @java.lang.Override

      public java.util.Map<java.lang.String, java.lang.String> getPropertiesMap() {
        return internalGetProperties().getMap();
      }

      /**
       * <code>map&lt;string, string&gt; properties = 3;</code>
       */
      @java.lang.Override

      public java.lang.String getPropertiesOrDefault(
          java.lang.String key,
          java.lang.String defaultValue) {
        if (key == null) {
          throw new NullPointerException("map key");
        }
        java.util.Map<java.lang.String, java.lang.String> map =
            internalGetProperties().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
      }

      /**
       * <code>map&lt;string, string&gt; properties = 3;</code>
       */
      @java.lang.Override

      public java.lang.String getPropertiesOrThrow(
          java.lang.String key) {
        if (key == null) {
          throw new NullPointerException("map key");
        }
        java.util.Map<java.lang.String, java.lang.String> map =
            internalGetProperties().getMap();
        if (!map.containsKey(key)) {
          throw new java.lang.IllegalArgumentException();
        }
        return map.get(key);
      }

      public Builder clearProperties() {
        internalGetMutableProperties().getMutableMap()
            .clear();
        return this;
      }

      /**
       * <code>map&lt;string, string&gt; properties = 3;</code>
       */

      public Builder removeProperties(
          java.lang.String key) {
        if (key == null) {
          throw new NullPointerException("map key");
        }
        internalGetMutableProperties().getMutableMap()
            .remove(key);
        return this;
      }

      /**
       * Use alternate mutation accessors instead.
       */
      @java.lang.Deprecated
      public java.util.Map<java.lang.String, java.lang.String>
      getMutableProperties() {
        return internalGetMutableProperties().getMutableMap();
      }

      /**
       * <code>map&lt;string, string&gt; properties = 3;</code>
       */
      public Builder putProperties(
          java.lang.String key,
          java.lang.String value) {
        if (key == null) {
          throw new NullPointerException("map key");
        }
        if (value == null) {
          throw new NullPointerException("map value");
        }

        internalGetMutableProperties().getMutableMap()
            .put(key, value);
        return this;
      }

      /**
       * <code>map&lt;string, string&gt; properties = 3;</code>
       */

      public Builder putAllProperties(
          java.util.Map<java.lang.String, java.lang.String> values) {
        internalGetMutableProperties().getMutableMap()
            .putAll(values);
        return this;
      }

      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }

      // @@protoc_insertion_point(builder_scope:Profile)
    }

    // @@protoc_insertion_point(class_scope:Profile)
    private static final org.beangle.security.session.protobuf.Model.Profile DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new org.beangle.security.session.protobuf.Model.Profile();
    }

    public static org.beangle.security.session.protobuf.Model.Profile getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Profile>
        PARSER = new com.google.protobuf.AbstractParser<Profile>() {
      @java.lang.Override
      public Profile parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Profile(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Profile> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Profile> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public org.beangle.security.session.protobuf.Model.Profile getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_Account_descriptor;
  private static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Account_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_Account_DetailsEntry_descriptor;
  private static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Account_DetailsEntry_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_Agent_descriptor;
  private static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Agent_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_Session_descriptor;
  private static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Session_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_Profile_descriptor;
  private static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Profile_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_Profile_PropertiesEntry_descriptor;
  private static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Profile_PropertiesEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
  getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;

  static {
    java.lang.String[] descriptorData = {
        "\n1org/beangle/security/session/protobuf/" +
            "model.proto\"\203\002\n\007Account\022\014\n\004name\030\001 \001(\t\022\023\n" +
            "\013description\030\002 \001(\t\022\022\n\ncategoryId\030\003 \001(\005\022\023" +
            "\n\013remoteToken\030\004 \001(\t\022\016\n\006status\030\005 \001(\005\022\023\n\013a" +
            "uthorities\030\006 \003(\t\022\023\n\013permissions\030\007 \003(\t\022\032\n" +
            "\010profiles\030\010 \003(\0132\010.Profile\022&\n\007details\030\t \003" +
            "(\0132\025.Account.DetailsEntry\032.\n\014DetailsEntr" +
            "y\022\013\n\003key\030\001 \001(\t\022\r\n\005value\030\002 \001(\t:\0028\001\"-\n\005Age" +
            "nt\022\014\n\004name\030\001 \001(\t\022\n\n\002ip\030\002 \001(\t\022\n\n\002os\030\003 \001(\t" +
            "\"\204\001\n\007Session\022\n\n\002id\030\001 \001(\t\022\033\n\tprincipal\030\002 " +
            "\001(\0132\010.Account\022\017\n\007loginAt\030\003 \001(\003\022\024\n\014lastAc" +
            "cessAt\030\004 \001(\003\022\025\n\005agent\030\005 \001(\0132\006.Agent\022\022\n\nt" +
            "tiSeconds\030\006 \001(\005\"\204\001\n\007Profile\022\n\n\002id\030\001 \001(\003\022" +
            "\014\n\004name\030\002 \001(\t\022,\n\nproperties\030\003 \003(\0132\030.Prof" +
            "ile.PropertiesEntry\0321\n\017PropertiesEntry\022\013" +
            "\n\003key\030\001 \001(\t\022\r\n\005value\030\002 \001(\t:\0028\001B.\n%org.be" +
            "angle.security.session.protobufB\005Modelb\006" +
            "proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
        .internalBuildGeneratedFileFrom(descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[]{
            });
    internal_static_Account_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_Account_fieldAccessorTable = new
        com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Account_descriptor,
        new java.lang.String[]{"Name", "Description", "CategoryId", "RemoteToken", "Status", "Authorities", "Permissions", "Profiles", "Details",});
    internal_static_Account_DetailsEntry_descriptor =
        internal_static_Account_descriptor.getNestedTypes().get(0);
    internal_static_Account_DetailsEntry_fieldAccessorTable = new
        com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Account_DetailsEntry_descriptor,
        new java.lang.String[]{"Key", "Value",});
    internal_static_Agent_descriptor =
        getDescriptor().getMessageTypes().get(1);
    internal_static_Agent_fieldAccessorTable = new
        com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Agent_descriptor,
        new java.lang.String[]{"Name", "Ip", "Os",});
    internal_static_Session_descriptor =
        getDescriptor().getMessageTypes().get(2);
    internal_static_Session_fieldAccessorTable = new
        com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Session_descriptor,
        new java.lang.String[]{"Id", "Principal", "LoginAt", "LastAccessAt", "Agent", "TtiSeconds",});
    internal_static_Profile_descriptor =
        getDescriptor().getMessageTypes().get(3);
    internal_static_Profile_fieldAccessorTable = new
        com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Profile_descriptor,
        new java.lang.String[]{"Id", "Name", "Properties",});
    internal_static_Profile_PropertiesEntry_descriptor =
        internal_static_Profile_descriptor.getNestedTypes().get(0);
    internal_static_Profile_PropertiesEntry_fieldAccessorTable = new
        com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Profile_PropertiesEntry_descriptor,
        new java.lang.String[]{"Key", "Value",});
  }

  // @@protoc_insertion_point(outer_class_scope)
}
